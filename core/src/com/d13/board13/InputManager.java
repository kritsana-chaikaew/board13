package com.d13.board13;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;

public class InputManager implements InputProcessor {
	public Manager manager;
	public Vector3 position = new Vector3();

	public InputManager (Manager manager) {
		this.manager = manager;
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		Actor actor = getGameObject(screenX, screenY);
		if(actor != null){
			return true;
		}
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		Actor actor = getGameObject(screenX, screenY);
		if (actor != null) {
			if(actor instanceof Character){
				actor.toggleSelection();
				manager.onActorClicked();	
			} else if(actor instanceof Tile){
				actor.toggleSelection();
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public Actor getGameObject (int screenX, int screenY) {
		Actor actor = null;
		Ray ray = manager.camera.getPickRay(screenX, screenY);
		float distance = -1;
		for (int i = 0; i < manager.tiles.size(); i++) {
			
			final Tile tile = manager.tiles.get(i);
			position = tile.getPosition();
			position.add(tile.getCenter());

	      	float len = ray.direction.dot(
					position.x-ray.origin.x,
	        		position.y-ray.origin.y,
					position.z-ray.origin.z);
	      	if (len < 0f) {
	        	continue;
	      	}
	        float dist2 = position.dst2(
					ray.origin.x+ray.direction.x*len,
	        		ray.origin.y+ray.direction.y*len,
					ray.origin.z+ray.direction.z*len);
	        if (distance >= 0f && dist2 > distance) {
	        	continue;
	        }

	        if (dist2 <= tile.getRadius() * tile.getRadius()) {
	        	Gdx.app.log("result", i + "");
	        	actor = tile;
	        	distance = dist2;
			}
		}

    	// Loop for characters
    	for (int i = 0; i < manager.characters.size(); i++) {

			final Character character = manager.characters.get(i);
			position = character.getPosition();
			position.add(character.getCenter());

      		float len = ray.direction.dot(
					position.x-ray.origin.x,
        			position.y-ray.origin.y,
					position.z-ray.origin.z);

      		if (len < 0f)
        		continue;

        	float dist2 = position.dst2(
					ray.origin.x+ray.direction.x*len,
          			ray.origin.y+ray.direction.y*len,
					ray.origin.z+ray.direction.z*len);

        	if (distance >= 0f && dist2 > distance)
          		continue;

        	if (dist2 <= character.getRadius() * character.getRadius()) {
          		actor = character;
          		distance = dist2;
			}
		}

		return actor;
	}
	
	public boolean isTouch(Actor actor) {
		for (int i = 0; i < manager.tiles.size(); i++) {
			if (actor == manager.tiles.get(i)) {
				return true;
			}
		}
		
		for (int i = 0; i < manager.characters.size(); i++) {
			if (actor == manager.characters.get(i)) {
				return true;
			}
		}
		return false;
	}
}

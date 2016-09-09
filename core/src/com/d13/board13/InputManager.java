package com.d13.board13;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;

public class InputManager implements InputProcessor {
	private Manager manager;

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

	private Actor getGameObject (int screenX, int screenY) {
		Actor object = null;
		Actor actor;
		Vector3 position;

		Ray ray = manager.getCamera().getPickRay(screenX, screenY);
		float distance = -1;

		for (int i = 0; i < manager.getActors().size(); i++) {
			actor = manager.getActors().get(i);
			position = actor.getPosition();
			position.add(actor.getCenter());

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

	        if (dist2 <= actor.getRadius() * actor.getRadius()) {
	        	Gdx.app.log("result", i + " ");
				distance = dist2;
				object =  actor;
			}
		}

		return object;
	}
}

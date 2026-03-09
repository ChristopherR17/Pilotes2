package com.christophercarrillo.drop2.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

/** AndroidLauncher completo con juego dentro y rebotes precisos */
public class AndroidLauncher extends AndroidApplication {

    private SpriteBatch batch;
    private FitViewport viewport;
    private Texture pilotaTexture;

    private float posx, posy;
    private float velx, vely;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AndroidApplicationConfiguration configuration = new AndroidApplicationConfiguration();
        configuration.useImmersiveMode = true;
        initialize(new AndroidApplicationAdapter(), configuration);
    }

    /** Clase interna que actúa como ApplicationAdapter */
    private class AndroidApplicationAdapter extends com.badlogic.gdx.ApplicationAdapter {

        @Override
        public void create() {
            batch = new SpriteBatch();
            viewport = new FitViewport(8, 5);

            Pixmap pixmap = new Pixmap(100, 100, Pixmap.Format.RGBA8888);
            pixmap.setColor(Color.RED);
            pixmap.fillCircle(50, 50, 50);
            pilotaTexture = new Texture(pixmap);
            pixmap.dispose();

            posx = 0f;
            posy = 0f;

            velx = 1f;
            vely = 1f;
        }

        @Override
        public void render() {
            float delta = Gdx.graphics.getDeltaTime();

            posx += velx * delta;
            posy += vely * delta;

            if (posx <= 0) {
                posx = 0;
                velx = -velx;
            } else if (posx + 1 >= viewport.getWorldWidth()) {
                posx = viewport.getWorldWidth() - 1;
                velx = -velx;
            }

            if (posy <= 0) {
                posy = 0;
                vely = -vely;
            } else if (posy + 1 >= viewport.getWorldHeight()) {
                posy = viewport.getWorldHeight() - 1;
                vely = -vely;
            }

            ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

            viewport.apply();
            batch.setProjectionMatrix(viewport.getCamera().combined);

            batch.begin();
            batch.draw(pilotaTexture, posx, posy, 1f, 1f);
            batch.end();
        }

        @Override
        public void resize(int width, int height) {
            viewport.update(width, height, true);
        }

        @Override
        public void dispose() {
            batch.dispose();
            pilotaTexture.dispose();
        }
    }
}

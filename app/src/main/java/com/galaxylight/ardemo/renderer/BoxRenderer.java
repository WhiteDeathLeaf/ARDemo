//================================================================================================================================
//
//  Copyright (c) 2015-2017 VisionStar Information Technology (Shanghai) Co., Ltd. All Rights Reserved.
//  EasyAR is the registered trademark or trademark of VisionStar Information Technology (Shanghai) Co., Ltd in China
//  and other countries for the augmented reality technology developed by VisionStar Information Technology (Shanghai) Co., Ltd.
//
//================================================================================================================================

package com.galaxylight.ardemo.renderer;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * Created by gzh on 2017-10-19.
 */
public class BoxRenderer extends BaseRenderer {
    private int pos_color_box;
    private int vbo_color_box;
    private int vbo_color_box_2;

    @Override
    public int setBoxVertInt() {
        return 4;
    }

    @Override
    public String setBoxVertString() {
        return "color";
    }

    @Override
    public String setBoxFragString() {
        return "";
    }

    @Override
    public String setGLFragColor() {
        return "vcolor;\n";
    }

    @Override
    public void instantiate() {
        pos_color_box = GLES20.glGetAttribLocation(program_box, "color");
        vbo_coord_box = generateOneBuffer();
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vbo_coord_box);
        float cube_vertices[][] = {{1.0f / 2, 1.0f / 2, 0.01f / 2}, {1.0f / 2, -1.0f / 2, 0.01f / 2}, {-1.0f / 2, -1.0f / 2, 0.01f / 2}, {-1.0f / 2, 1.0f / 2, 0.01f / 2}, {1.0f / 2, 1.0f / 2, -0.01f / 2}, {1.0f / 2, -1.0f / 2, -0.01f / 2}, {-1.0f / 2, -1.0f / 2, -0.01f / 2}, {-1.0f / 2, 1.0f / 2, -0.01f / 2}};
        FloatBuffer cube_vertices_buffer = FloatBuffer.wrap(flatten(cube_vertices));
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, cube_vertices_buffer.limit() * 4, cube_vertices_buffer, GLES20.GL_DYNAMIC_DRAW);

        vbo_color_box = generateOneBuffer();
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vbo_color_box);
        int cube_vertex_colors[][] = {{255, 0, 0, 128}, {0, 255, 0, 128}, {0, 0, 255, 128}, {0, 0, 0, 128}, {0, 255, 255, 128}, {255, 0, 255, 128}, {255, 255, 0, 128}, {255, 255, 255, 128}};
        ByteBuffer cube_vertex_colors_buffer = ByteBuffer.wrap(byteArrayFromIntArray(flatten(cube_vertex_colors)));
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, cube_vertex_colors_buffer.limit(), cube_vertex_colors_buffer, GLES20.GL_STATIC_DRAW);

        vbo_color_box_2 = generateOneBuffer();
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vbo_color_box_2);
        int cube_vertex_colors_2[][] = {{255, 0, 0, 255}, {255, 255, 0, 255}, {0, 255, 0, 255}, {255, 0, 255, 255}, {255, 0, 255, 255}, {255, 255, 255, 255}, {0, 255, 255, 255}, {255, 0, 255, 255}};
        ByteBuffer cube_vertex_colors_2_buffer = ByteBuffer.wrap(byteArrayFromIntArray(flatten(cube_vertex_colors_2)));
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, cube_vertex_colors_2_buffer.limit(), cube_vertex_colors_2_buffer, GLES20.GL_STATIC_DRAW);

        vbo_faces_box = generateOneBuffer();
        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, vbo_faces_box);
        short cube_faces[][] = {{3, 2, 1, 0}, {2, 3, 7, 6}, {0, 1, 5, 4}, {3, 0, 4, 7}, {1, 2, 6, 5}, {4, 5, 6, 7}};
        ShortBuffer cube_faces_buffer = ShortBuffer.wrap(flatten(cube_faces));
        GLES20.glBufferData(GLES20.GL_ELEMENT_ARRAY_BUFFER, cube_faces_buffer.limit() * 2, cube_faces_buffer, GLES20.GL_STATIC_DRAW);

    }

    @Override
    public void setGLES20(float size0, float size1) {
        float cube_vertices[][] = {{size0 / 2, size1 / 2, size0 / 1000 / 2}, {size0 / 2, -size1 / 2, size0 / 1000 / 2}, {-size0 / 2, -size1 / 2, size0 / 1000 / 2}, {-size0 / 2, size1 / 2, size0 / 1000 / 2}, {size0 / 2, size1 / 2, 0}, {size0 / 2, -size1 / 2, 0}, {-size0 / 2, -size1 / 2, 0}, {-size0 / 2, size1 / 2, 0}};
        FloatBuffer cube_vertices_buffer = FloatBuffer.wrap(flatten(cube_vertices));
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, cube_vertices_buffer.limit() * 4, cube_vertices_buffer, GLES20.GL_DYNAMIC_DRAW);
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vbo_color_box);
        GLES20.glEnableVertexAttribArray(pos_color_box);
        GLES20.glVertexAttribPointer(pos_color_box, 4, GLES20.GL_UNSIGNED_BYTE, true, 0, 0);
        for (int i = 0; i < 6; i++) {
            GLES20.glDrawElements(GLES20.GL_TRIANGLE_FAN, 4, GLES20.GL_UNSIGNED_SHORT, i * 4 * 2);
        }
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vbo_coord_box);
        float cube_vertices_2[][] = {{size0 / 4, size1 / 4, size0 / 4}, {size0 / 4, -size1 / 4, size0 / 4}, {-size0 / 4, -size1 / 4, size0 / 4}, {-size0 / 4, size1 / 4, size0 / 4}, {size0 / 4, size1 / 4, 0}, {size0 / 4, -size1 / 4, 0}, {-size0 / 4, -size1 / 4, 0}, {-size0 / 4, size1 / 4, 0}};
        FloatBuffer cube_vertices_2_buffer = FloatBuffer.wrap(flatten(cube_vertices_2));
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, cube_vertices_2_buffer.limit() * 4, cube_vertices_2_buffer, GLES20.GL_DYNAMIC_DRAW);
        GLES20.glEnableVertexAttribArray(pos_coord_box);
        GLES20.glVertexAttribPointer(pos_coord_box, 3, GLES20.GL_FLOAT, false, 0, 0);
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vbo_color_box_2);
        GLES20.glEnableVertexAttribArray(pos_color_box);
        GLES20.glVertexAttribPointer(pos_color_box, 4, GLES20.GL_UNSIGNED_BYTE, true, 0, 0);
        for (int i = 0; i < 6; i++) {
            GLES20.glDrawElements(GLES20.GL_TRIANGLE_FAN, 4, GLES20.GL_UNSIGNED_SHORT, i * 4 * 2);
        }
    }
}

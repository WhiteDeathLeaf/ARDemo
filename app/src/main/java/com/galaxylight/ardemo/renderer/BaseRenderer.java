package com.galaxylight.ardemo.renderer;

import android.opengl.GLES20;

import cn.easyar.Matrix44F;
import cn.easyar.Vec2F;

/**
 * Created by gzh on 2017-10-19.
 */

public abstract class BaseRenderer {
    public int program_box;
    public int pos_coord_box;
    public int pos_trans_box;
    public int pos_proj_box;
    public int vbo_coord_box;
    public int vbo_faces_box;

    public String box_vert = "uniform mat4 trans;uniform mat4 proj;attribute vec4 coord;"
            + "attribute vec" + setBoxVertInt() + " " + setBoxVertString() + ";"
            + "varying vec" + setBoxVertInt() + " v" + setBoxVertString() + ";"
            + "void main(void){v" + setBoxVertString() + "=" + setBoxVertString() + ";gl_Position = proj*trans*coord;}";

    public String box_frag = "#ifdef GL_ES precision highp float;#endif"
            + "varying vec" + setBoxVertInt() + " v" + setBoxVertString() + ";"
            + setBoxFragString() + "void main(void){gl_FragColor =" + setGLFragColor() + ";}";

    public abstract int setBoxVertInt();

    public abstract String setBoxVertString();

    public abstract String setBoxFragString();

    public abstract String setGLFragColor();

    public float[] flatten(float[][] a) {
        int size = 0;
        for (float[] anA : a) {
            size += anA.length;
        }
        float[] l = new float[size];
        int offset = 0;
        for (float[] anA : a) {
            System.arraycopy(anA, 0, l, offset, anA.length);
            offset += anA.length;
        }
        return l;
    }

    public int[] flatten(int[][] a) {
        int size = 0;
        for (int[] anA : a) {
            size += anA.length;
        }
        int[] l = new int[size];
        int offset = 0;
        for (int[] anA : a) {
            System.arraycopy(anA, 0, l, offset, anA.length);
            offset += anA.length;
        }
        return l;
    }

    public short[] flatten(short[][] a) {
        int size = 0;
        for (short[] anA : a) {
            size += anA.length;
        }
        short[] l = new short[size];
        int offset = 0;
        for (short[] anA : a) {
            System.arraycopy(anA, 0, l, offset, anA.length);
            offset += anA.length;
        }
        return l;
    }

    public byte[] flatten(byte[][] a) {
        int size = 0;
        for (byte[] anA : a) {
            size += anA.length;
        }
        byte[] l = new byte[size];
        int offset = 0;
        for (byte[] anA : a) {
            System.arraycopy(anA, 0, l, offset, anA.length);
            offset += anA.length;
        }
        return l;
    }

    public byte[] byteArrayFromIntArray(int[] a) {
        byte[] l = new byte[a.length];
        for (int k = 0; k < a.length; k += 1) {
            l[k] = (byte) (a[k] & 0xFF);
        }
        return l;
    }

    public int generateOneBuffer() {
        int[] buffer = {0};
        GLES20.glGenBuffers(1, buffer, 0);
        return buffer[0];
    }

    public int generateOneTexture() {
        int[] buffer = {0};
        GLES20.glGenTextures(1, buffer, 0);
        return buffer[0];
    }

    public void init() {
        program_box = GLES20.glCreateProgram();
        int vertShader = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER);
        GLES20.glShaderSource(vertShader, box_vert);
        GLES20.glCompileShader(vertShader);
        int fragShader = GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER);
        GLES20.glShaderSource(fragShader, box_frag);
        GLES20.glCompileShader(fragShader);
        GLES20.glAttachShader(program_box, vertShader);
        GLES20.glAttachShader(program_box, fragShader);
        GLES20.glLinkProgram(program_box);
        GLES20.glUseProgram(program_box);
        pos_coord_box = GLES20.glGetAttribLocation(program_box, "coord");
        pos_trans_box = GLES20.glGetUniformLocation(program_box, "trans");
        pos_proj_box = GLES20.glGetUniformLocation(program_box, "proj");
        instantiate();
    }

    public abstract void instantiate();

    public void render(Matrix44F projectionMatrix, Matrix44F cameraview, Vec2F size) {
        float size0 = size.data[0];
        float size1 = size.data[1];
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vbo_coord_box);
        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        GLES20.glUseProgram(program_box);
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vbo_coord_box);
        GLES20.glEnableVertexAttribArray(pos_coord_box);
        GLES20.glVertexAttribPointer(pos_coord_box, 3, GLES20.GL_FLOAT, false, 0, 0);
        GLES20.glUniformMatrix4fv(pos_trans_box, 1, false, cameraview.data, 0);
        GLES20.glUniformMatrix4fv(pos_proj_box, 1, false, projectionMatrix.data, 0);
        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, vbo_faces_box);
        setGLES20(size0, size1);
    }

    public abstract void setGLES20(float size0, float size1);
}

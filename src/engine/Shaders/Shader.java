package engine.Shaders;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

public abstract class Shader {
    private int vertexShaderID;
    private int fragmentShaderId;
    private int programID;

    private String fragmentFile;
    private String vertexFile;
    public Shader(String vertexFile, String fragmentFile) {
        this.fragmentFile = fragmentFile;
        this.vertexFile = vertexFile;
    }

    public void create() {
        vertexShaderID = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
        GL20.glShaderSource(vertexShaderID, readFile(vertexFile));
        GL20.glCompileShader(vertexShaderID);

        if (GL20.glGetShaderi(vertexShaderID, GL20.GL_COMPILE_STATUS) == GL20.GL_FALSE){
            System.err.println("Error: Vertex Shader - " + GL20.glGetShaderInfoLog(vertexShaderID));
            System.exit(-1);
        }

        fragmentShaderId = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
        GL20.glShaderSource(fragmentShaderId, readFile(fragmentFile));
        GL20.glCompileShader(fragmentShaderId);

        if (GL20.glGetShaderi(fragmentShaderId, GL20.GL_COMPILE_STATUS) == GL20.GL_FALSE){
            System.err.println("Error: Fragment Shader - " + GL20.glGetShaderInfoLog(fragmentShaderId));
            System.exit(-1);
        }

        programID = GL20.glCreateProgram();

        GL20.glAttachShader(programID, fragmentShaderId);
        GL20.glAttachShader(programID, vertexShaderID);

        GL20.glLinkProgram(programID);
        GL20.glValidateProgram(programID);

        getAllUniforms();

        if (GL20.glGetProgrami(programID, GL20.GL_LINK_STATUS) == GL20.GL_FALSE){
            System.err.println("Error: Failed to link shaders to program.");
        }

        if (GL20.glGetProgrami(programID, GL20.GL_VALIDATE_STATUS) == GL20.GL_FALSE){
            System.err.println("Error: Failed to validate shaders to program.");
        }
    }

    public void bind() {
        GL20.glUseProgram(programID);
    }

    public void remove() {
        GL20.glDetachShader(programID, vertexShaderID);
        GL20.glDetachShader(programID, fragmentShaderId);

        GL20.glDeleteShader(vertexShaderID);
        GL20.glDeleteShader(fragmentShaderId);

        GL20.glDeleteProgram(programID);
    }

    private String readFile(String file) {
        BufferedReader reader = null;
        StringBuilder string = new StringBuilder();

        try {
            reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                string.append(line).append("\n");
            }
        } catch (IOException e) {
            System.err.println("Failed to read :" + file);
        }
        return string.toString();
    }

    public abstract void bindAllAttributes();

    protected abstract void getAllUniforms();

    protected int getUniform(final String name) {
        return GL20.glGetUniformLocation(programID, name);
    }

    protected void loadFloatUniform(int location, float value) {
        GL20.glUniform1f(location, value);
    }

    protected void loadIntUniform(final int location, int value) {
        GL20.glUniform1i(location, value);
    }

    protected void loadVectorUniform(final int location, Vector3f value) {
        GL20.glUniform3f(location, value.x, value.y, value.z);
    }

    protected void loadMatrixUniform(final int location, Matrix4f value) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
        value.get(buffer);
        buffer.flip();
        GL20.glUniformMatrix4fv(location, false, buffer);
    }

    public void bindAttribute(int index, String location) {
        GL20.glBindAttribLocation(programID, index, location);
    }
}

package engine.Shaders;

public class BasicShader extends Shader {
    private static final String VERTEX_FILE = "./src/engine/Shaders/basicVertexShader.glsl";
    private static final String FRAGMENT_FILE = "./src/engine/Shaders/basicFragmentShader.glsl";

    private int scaleLocation;

    public BasicShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void getAllUniforms() {
        scaleLocation = super.getUniform("scale");
    }

    @Override
    public void bindAllAttributes() {
        super.bindAttribute(0, "vertices");
        super.bindAttribute(1, "textCoords");
    }

    public void loadScale(float number) {
        super.loadFloatUniform(scaleLocation, number);
    }

}

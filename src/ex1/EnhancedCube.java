package ex1;

import javax.swing.JFrame;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.gl2.GLUT;

public class EnhancedCube implements GLEventListener {

    private float angleX = 0.0f; // Góc xoay quanh trục X
    private float angleY = 0.0f; // Góc xoay quanh trục Y

    public static void main(String[] args) {
        JFrame frame = new JFrame("Enhanced Rotating Cube");
        GLProfile profile = GLProfile.getDefault();
        GLCapabilities capabilities = new GLCapabilities(profile);

        GLCanvas canvas = new GLCanvas(capabilities);
        EnhancedCube enhancedCube = new EnhancedCube();

        canvas.addGLEventListener(enhancedCube);
        frame.add(canvas);
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        Animator animator = new Animator(canvas);
        animator.start();
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClearColor(0.0f,0.0f, 0.0f, 1.0f); // Màu nền

        // Bật kiểm tra độ sâu
        gl.glEnable(GL2.GL_DEPTH_TEST);
 
        // Thiết lập ánh sáng
        gl.glEnable(GL2.GL_LIGHTING);
        gl.glEnable(GL2.GL_LIGHT0);

        float[] lightPosition = { 1.0f, 1.0f, 0.0f, 0.0f }; // Vị trí ánh sáng
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, lightPosition, 0);
//
        float[] ambientLight = { 0.2f, 0.5f, 0.2f, 1.0f }; // Ánh sáng môi trường
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, ambientLight, 0);

        float[] diffuseLight = { 1.0f, 1.0f, 1.0f, 1.0f }; // Ánh sáng khuếch tán
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, diffuseLight, 0);
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl = drawable.getGL().getGL2();
        if (height <= 0) height = 1;
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        GLU glu = new GLU();
        glu.gluPerspective(45.0, (double) width / height, 0.1, 100.0);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();

        // Di chuyển camera
        gl.glTranslatef(0.0f, 0.0f, -10.0f);

        // Xoay khối lập phương
        gl.glRotatef(angleX, 1.0f, 0.0f, 0.0f); // Xoay quanh trục X
        gl.glRotatef(angleY, 0.0f, 1.0f, 0.0f); // Xoay quanh trục Y

        //ve khoi nao
        
        gl.glPushMatrix(); // Lưu trạng thái hiện tại
        gl.glTranslatef(-2.0f, 0.0f, 0.0f);
        int numSides = 30; // Số mặt của nón
        float radius = 1.0f; // Bán kính đáy
        float height = 2.0f; 
    
        gl.glBegin(GL2.GL_TRIANGLE_FAN);
        gl.glColor3f(1.0f, 0.5f, 0.0f); // Màu nón
        gl.glVertex3f(0.0f, height, 0.0f); // Đỉnh của nón

        for (int i = 0; i <= numSides; i++) {
            double angle = 2 * Math.PI * i / numSides; // Tính góc
            float x = (float) (radius * Math.cos(angle)); // Tính tọa độ X
            float z = (float) (radius * Math.sin(angle)); // Tính tọa độ Z
            gl.glVertex3f(x, 0.0f, z); // Thêm đỉnh
        }
        gl.glEnd();

        // Vẽ đáy nón
        gl.glBegin(GL2.GL_QUAD_STRIP);
        float[] materialDiffuseBottom2 = { 1.0f, 1.0f, 0.0f, 1.0f }; // Màu vàng
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, materialDiffuseBottom2, 0);
        gl.glNormal3f(0.0f, -1.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glVertex3f( 1.0f, -1.0f, -1.0f);
        gl.glVertex3f( 1.0f, -1.0f,  1.0f);
        gl.glVertex3f(-1.0f, -1.0f,  1.0f);
        
        gl.glEnd();
        
        gl.glPopMatrix();
        // Vẽ khối lập phương
        
        gl.glPushMatrix(); // Lưu trạng thái hiện tại
        gl.glTranslatef(2.0f, 0.0f, 0.0f);
        
        gl.glBegin(GL2.GL_QUADS); // Bắt đầu vẽ các mặt của khối lập phương

        // Mặt trước
        float[] materialDiffuseFront = { 1.0f, 0.0f, 0.0f, 1.0f }; // Màu đỏ
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, materialDiffuseFront, 0);
        gl.glNormal3f(0.0f, 0.0f, 1.0f);
        gl.glVertex3f(-1.0f, -1.0f,  1.0f); // Góc dưới trái
        gl.glVertex3f( 1.0f, -1.0f,  1.0f); // Góc dưới phải
        gl.glVertex3f( 1.0f,  1.0f,  1.0f); // Góc trên phải
        gl.glVertex3f(-1.0f,  1.0f,  1.0f); // Góc trên trái

        // Mặt sau
        float[] materialDiffuseBack = { 0.0f, 1.0f, 0.0f, 1.0f }; // Màu xanh lá
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, materialDiffuseBack, 0);
        gl.glNormal3f(0.0f, 0.0f, -1.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glVertex3f(-1.0f,  1.0f, -1.0f);
        gl.glVertex3f( 1.0f,  1.0f, -1.0f);
        gl.glVertex3f( 1.0f, -1.0f, -1.0f);

        // Mặt trên
        float[] materialDiffuseTop = { 0.0f, 0.0f, 1.0f, 1.0f }; // Màu xanh dương
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, materialDiffuseTop, 0);
        gl.glNormal3f(0.0f, 1.0f, 0.0f);
        gl.glVertex3f(-1.0f,  1.0f, -1.0f);
        gl.glVertex3f(-1.0f,  1.0f,  1.0f);
        gl.glVertex3f( 1.0f,  1.0f,  1.0f);
        gl.glVertex3f( 1.0f,  1.0f, -1.0f);

        // Mặt dưới
        float[] materialDiffuseBottom = { 1.0f, 1.0f, 0.0f, 1.0f }; // Màu vàng
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, materialDiffuseBottom, 0);
        gl.glNormal3f(0.0f, -1.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glVertex3f( 1.0f, -1.0f, -1.0f);
        gl.glVertex3f( 1.0f, -1.0f,  1.0f);
        gl.glVertex3f(-1.0f, -1.0f,  1.0f);

        // Mặt bên trái
        float[] materialDiffuseLeft = { 1.0f, 0.0f, 1.0f, 1.0f }; // Màu tím
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, materialDiffuseLeft, 0);
        gl.glNormal3f(-1.0f, 0.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glVertex3f(-1.0f, -1.0f,  1.0f);
        gl.glVertex3f(-1.0f,  1.0f,  1.0f);
        gl.glVertex3f(-1.0f,  1.0f, -1.0f);

        // Mặt bên phải
        float[] materialDiffuseRight = { 0.0f, 1.0f, 1.0f, 1.0f }; // Màu cyan
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, materialDiffuseRight, 0);
        gl.glNormal3f(1.0f, 0.0f, 0.0f);
        gl.glVertex3f( 1.0f, -1.0f, -1.0f);
        gl.glVertex3f( 1.0f,  1.0f, -1.0f);
        gl.glVertex3f( 1.0f,  1.0f,  1.0f);
        gl.glVertex3f( 1.0f, -1.0f,  1.0f);

        gl.glEnd();// Kết thúc vẽ khối lập phương
        gl.glPopMatrix();

        // Cập nhật góc xoay
        angleX += 0.5f;
        angleY += 0.5f;

        gl.glFlush();
    }
}



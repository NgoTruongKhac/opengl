package ex1;

import javax.swing.JFrame;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.gl2.GLUT;

public class Teapot3D implements GLEventListener {
    private GLUT glut = new GLUT();
    private float angle = 0.0f;

    public static void main(String[] args) {
        // Tạo khung JFrame và thêm canvas OpenGL vào
        JFrame frame = new JFrame("3D Teapot using JOGL");
        GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);
        GLCanvas canvas = new GLCanvas(capabilities);
        
        Teapot3D renderer = new Teapot3D();
        canvas.addGLEventListener(renderer);
        canvas.setSize(400, 400);
        
        frame.getContentPane().add(canvas);
        frame.setSize(frame.getContentPane().getPreferredSize());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
        // Tạo animator để liên tục vẽ lại khung hình
        FPSAnimator animator = new FPSAnimator(canvas, 60);
        animator.start();
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        GLU glu = new GLU();
        
        // Xóa màu nền và buffer độ sâu
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        
        // Đặt ma trận chế độ mô hình
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
        
        
        
  
        glu.gluLookAt(0.0, 5.0, 0.0, // Vị trí camera (x, y, z)
                0.0, 0.0, 0.0, // Điểm nhìn vào (trung tâm)
                0.0, 0.0, -1.0); // Vector lên (hướng Y)
        // Di chuyển đối tượng vào không gian nhìn thấy
        gl.glTranslatef(0.0f, -3.0f, 0.0f);
        
        // Xoay ấm trà quanh trục Y
        gl.glRotatef(angle, 0.0f, 1.0f, 0.0f);
        angle += 0.5f;  // Tốc độ xoay
        
        // Đặt màu ấm trà
        float[] materialDiffuseFront = { 0.2f, 1.0f, 0.3f, 1.0f }; // Màu đỏ
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, materialDiffuseFront, 0);
        
        // Vẽ ấm trà đặc
        glut.glutSolidTeapot(1.0f);
        
        gl.glFlush();
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        // Khởi tạo các cấu hình OpenGL
        GL2 gl = drawable.getGL().getGL2();
        
        // Bật cài đặt độ sâu để hiển thị vật thể đúng thứ tự
        gl.glEnable(GL.GL_DEPTH_TEST);
        
        // Đặt chế độ tô màu mặt ngoài
        gl.glShadeModel(GL2.GL_SMOOTH);
        // Đặt nền màu đen
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        
        
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
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl = drawable.getGL().getGL2();
        
        // Đặt ma trận chế độ phối cảnh
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        
        // Tạo góc nhìn phối cảnh
        GLU glu = new GLU();
        glu.gluPerspective(45.0, (float) width / height, 1.0, 100.0);
        
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
        // Giải phóng tài nguyên nếu cần
    }
}

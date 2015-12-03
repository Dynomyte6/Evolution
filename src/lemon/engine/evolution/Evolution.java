package lemon.engine.evolution;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;

import lemon.engine.control.GLFWWindow;
import lemon.engine.control.GLFWWindowSettings;
import lemon.engine.event.EventManager;
import lemon.engine.event.Listener;
import lemon.engine.event.Subscribe;
import lemon.engine.input.KeyEvent;

public class Evolution {
	public static void main(String[] args){
		GLFWWindowSettings settings = new GLFWWindowSettings(){
			@Override
			public long createWindow() {
				GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GL11.GL_FALSE);
				GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
				GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 3);
				GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);
				GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, 1);
				GLFWVidMode vidmode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
				return GLFW.glfwCreateWindow(vidmode.width(), vidmode.height(), "Game "+Version.getVersion(), GLFW.glfwGetPrimaryMonitor(), MemoryUtil.NULL);
				//return GLFW.glfwCreateWindow(800, 600, "Title", MemoryUtil.NULL, MemoryUtil.NULL);
			}
			@Override
			public int getTargetFrameRate() {
				return 60;
			}
		};
		final GLFWWindow window = new GLFWWindow(settings);
		try{
			EventManager.INSTANCE.registerListener(Game.INSTANCE);
			EventManager.INSTANCE.registerListener(new Listener(){
				@Subscribe
				public void onKeyPress(KeyEvent event){
					if(event.getKey()==GLFW.GLFW_KEY_ESCAPE){
						GLFW.glfwSetWindowShouldClose(window.getId(), GL11.GL_TRUE);
					}
				}
			});
			window.init();
			window.run();
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			window.dump();
		}
	}
}

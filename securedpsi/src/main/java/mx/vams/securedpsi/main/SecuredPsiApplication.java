package mx.vams.securedpsi.main;

import java.io.Serializable;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.application.EntryPoint;
import org.eclipse.rap.rwt.service.UISessionEvent;
import org.eclipse.rap.rwt.service.UISessionListener;
import org.eclipse.swt.widgets.Display;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import mx.vams.securedpsi.view.MainShell;
import mx.vams.securedpsi.view.util.WidgetsUtil;

@SuppressWarnings("unused")
public class SecuredPsiApplication implements EntryPoint, Serializable {

	private static final long serialVersionUID = 2770452134992984148L;

	public int createUI() {
		RWT.getUISession().getHttpSession().setMaxInactiveInterval(3 * 60);

		// final ApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(RWT.getRequest().getSession().getServletContext());
		// prepareFlosolSystem(context);
		
		MainShell sh = new MainShell(Display.getCurrent());

		// vamsLoginShell = new VamsLoginShell(Display.getCurrent());
		// vamsLoginShell.setGenericUserService((GenericUsersService) context.getBean("genericUsersService"));

		RWT.getUISession().getHttpSession().setMaxInactiveInterval(30 * 60);

		// Esto va a correr solamente cuando cuando cierre la aplicación...
		Display.getCurrent().disposeExec(new Runnable() {
			public void run() {
				// Despose resources ...
				System.out.println( "Last session... 1" );
			}
		});

		// Esto va a correr solamente cuando cuando cierre la aplicación...
		RWT.getUISession().addUISessionListener(new UISessionListener() {
			private static final long serialVersionUID = 54266129521689834L;

			public void beforeDestroy(UISessionEvent event) {
				// Despose resources ...
				System.out.println( "Last session... 1" );
			}
		});

		WidgetsUtil.inicializarShellCentrado(sh);

		return 0;
	}

}
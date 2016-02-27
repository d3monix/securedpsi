package mx.vams.securedpsi.main;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;

import org.eclipse.rap.rwt.application.Application;
import org.eclipse.rap.rwt.application.Application.OperationMode;
import org.eclipse.rap.rwt.application.ApplicationConfiguration;
import org.eclipse.rap.rwt.client.WebClient;

public class SecuredPsiApplicationConfiguration implements ApplicationConfiguration, Serializable {
//	private static final Log	log					= LogFactory.getLog( SecuredPsiApplicationConfiguration.class );
	private static final long	serialVersionUID	= -432136413462799214L;

	public void configure( Application application ) {
		Map< String, String > properties = new HashMap< String, String >();
		properties.put( WebClient.PAGE_TITLE, "::: BILLPOCKET :::" );
		application.setOperationMode( OperationMode.SWT_COMPATIBILITY );
		
		// application.addStyleSheet( "mx.vams.flosol.contratos.view.css", "flosol_style/theme/default.css" );
		// properties.put( WebClient.THEME_ID, "mx.vams.flosol.contratos.view.css" );
		// log.info( "Style loaded " + "mx.vams.flosol.contratos.view.css" );
		
		application.addEntryPoint( "/app", SecuredPsiApplication.class, properties );
	}
}
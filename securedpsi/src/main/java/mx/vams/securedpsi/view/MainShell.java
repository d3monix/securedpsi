package mx.vams.securedpsi.view;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import mx.vams.securedpsi.view.util.WidgetsUtil;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class MainShell extends Shell {

	private static final long serialVersionUID = -4498534975515755055L;

	public MainShell(Display display) {
		super(display);
		createContents();
	}

	protected void createContents() {
		setSize(450, 300);
		setText("Sample Shell");

		Button btnNewButton = new Button(this, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {

			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				WidgetsUtil.openDialog( getShell(), "Advertencia", "" + "Rubén es homosexual", SWT.ICON_INFORMATION );
				System.out.println("Nasty bitch!");
			}
		});

		btnNewButton.setBounds(10, 159, 271, 109);
		btnNewButton.setText("PUSH Me bitch!");
	}
}
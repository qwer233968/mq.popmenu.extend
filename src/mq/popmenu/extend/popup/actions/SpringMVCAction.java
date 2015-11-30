package mq.popmenu.extend.popup.actions;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import mq.popmenu.extend.dialogs.SpringDialog;

public class SpringMVCAction implements IObjectActionDelegate {

	private Shell shell;
	
	 IStructuredSelection selection = null;

	/**
	 * Constructor for Action1.
	 */
	public SpringMVCAction() {
		super();
	}

	/**
	 * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		shell = targetPart.getSite().getShell();
	}

	/**
	 * @see IActionDelegate#run(IAction)
	 */
	public void run(IAction action) {
		final Object obj = selection.getFirstElement();
		if(obj instanceof IJavaProject){
			final IJavaProject project = (IJavaProject) obj;
			 IFile iFile_pom = project.getProject().getFile("pom.xml");
			 IFile iFile_web = project.getProject().getFile("src/main/webapp/WEB-INF/web.xml");
			 if(!iFile_pom.exists() || !iFile_web.exists()){
				 MessageDialog.openInformation(
							shell,
							"Error",
							"这不是一个maven-webapp项目.");
			 }else{
				 Dialog dialog = new SpringDialog(shell, project);
				 dialog.open();
			 }
		}
	}
	
	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		if (selection != null && selection instanceof IStructuredSelection) {
			  this.selection = (IStructuredSelection) selection;
		}
	}

}

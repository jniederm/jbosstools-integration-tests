package org.jboss.tools.bpel.ui.bot.test;

import org.eclipse.swtbot.swt.finder.SWTBotTestCase;
import org.jboss.reddeer.swt.util.Bot;
import org.jboss.tools.bpel.reddeer.editor.BpelEditor;
import org.jboss.tools.bpel.reddeer.wizard.NewProcessWizard;
import org.jboss.tools.bpel.reddeer.wizard.NewProjectWizard;
import org.jboss.tools.bpel.ui.bot.test.suite.CleanWorkspaceRequirement.CleanWorkspace;
import org.jboss.tools.bpel.ui.bot.test.suite.PerspectiveRequirement.Perspective;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * 
 * @author apodhrad
 * 
 */
@CleanWorkspace
@Perspective(name = "BPEL")
public class WizardTest extends SWTBotTestCase {

	@BeforeClass
	public static void closeWelcome() throws Exception {
		try {
			Bot.get().viewByTitle("Welcome").close();
		} catch (Exception ex) {
			//
		}
	}

	@Test
	public void createNewSyncProcess() throws Exception {
		String projectName = "SyncProcessProject";
		String processName = "SyncProcess";

		new NewProjectWizard(projectName).execute();

		NewProcessWizard wizard = new NewProcessWizard(projectName, processName);
		wizard.setTemplate("Synchronous BPEL Process");
		wizard.execute();

		BpelEditor bpelEditor = new BpelEditor(processName + ".bpel");
		String processContent = bpelEditor.getSource();

		Assert.assertTrue(processContent != null);
		Assert.assertTrue(processContent.contains("http://docs.oasis-open.org/wsbpel/2.0/process/executable"));
		Assert.assertTrue(processContent.contains("<bpel:import location=\"SyncProcessArtifacts.wsdl\""));
		Assert.assertTrue(processContent.contains("<bpel:receive name=\"receiveInput\""));
		Assert.assertTrue(processContent.contains("<bpel:reply name=\"replyOutput\""));
	}

	@Test
	public void createNewAsyncProcess() throws Exception {
		String projectName = "AsyncProcessProject";
		String processName = "AsyncProcess";

		new NewProjectWizard(projectName).execute();

		NewProcessWizard wizard = new NewProcessWizard(projectName, processName);
		wizard.setTemplate("Asynchronous BPEL Process");
		wizard.execute();

		BpelEditor bpelEditor = new BpelEditor(processName + ".bpel");
		String processContent = bpelEditor.getSource();

		Assert.assertTrue(processContent != null);
		Assert.assertTrue(processContent.contains("http://docs.oasis-open.org/wsbpel/2.0/process/executable"));
		Assert.assertTrue(processContent.contains("<bpel:import location=\"AsyncProcessArtifacts.wsdl\""));
		Assert.assertTrue(processContent.contains("<bpel:receive name=\"receiveInput\""));
		Assert.assertTrue(processContent.contains("<bpel:invoke name=\"callbackClient\""));
	}

	@Test
	public void createNewEmptyProcess() throws Exception {
		String projectName = "EmptyProcessProject";
		String processName = "EmptyProcess";

		new NewProjectWizard(projectName).execute();

		NewProcessWizard wizard = new NewProcessWizard(projectName, processName);
		wizard.setTemplate("Empty BPEL Process");
		wizard.execute();

		BpelEditor bpelEditor = new BpelEditor(processName + ".bpel");
		String processContent = bpelEditor.getSource();

		Assert.assertTrue(processContent != null);
		Assert.assertTrue(processContent.contains("http://docs.oasis-open.org/wsbpel/2.0/process/executable"));
		Assert.assertTrue(processContent.contains("<bpel:import location=\"EmptyProcessArtifacts.wsdl\""));
		Assert.assertTrue(processContent.contains("<bpel:sequence name=\"main\">"));
		Assert.assertTrue(processContent.contains("<bpel:empty name=\"Empty\"></bpel:empty>"));

		Assert.assertFalse(processContent.contains("<bpel:receive name=\"receiveInput\""));
	}

	@Test
	public void createNewAbstractSyncProcess() {
		String projectName = "AbstractProcessProject";
		String processName = "AbstractProcess";

		new NewProjectWizard(projectName).execute();

		NewProcessWizard wizard = new NewProcessWizard(projectName, processName);
		wizard.setTemplate("Synchronous BPEL Process");
		wizard.setAbstract(true);
		wizard.execute();

		BpelEditor bpelEditor = new BpelEditor(processName + ".bpel");
		String processContent = bpelEditor.getSource();

		Assert.assertTrue(processContent != null);
		Assert.assertTrue(processContent.contains("http://docs.oasis-open.org/wsbpel/2.0/process/abstract"));
		Assert.assertTrue(processContent.contains("<bpel:import location=\"AbstractProcessArtifacts.wsdl\""));
		Assert.assertTrue(processContent.contains("<bpel:receive name=\"receiveInput\""));
		Assert.assertTrue(processContent.contains("<bpel:reply name=\"replyOutput\""));
	}
}

package org.jboss.tools.ui.bot.ext.config.requirement;

import org.jboss.tools.ui.bot.ext.SWTTestExt;
import org.jboss.tools.ui.bot.ext.config.TestConfigurator;

public class AddESB extends RequirementBase {

	@Override
	public boolean checkFulfilled() {
		return SWTTestExt.configuredState.getEsb().isConfiured;
	}

	@Override
	public void handle() {
		String esbName = "ESB-"+TestConfigurator.esb.version;
		SWTTestExt.eclipse.addESBRuntime(esbName,TestConfigurator.esb.version,TestConfigurator.esb.esbHome);
		SWTTestExt.configuredState.getEsb().isConfiured=true;
		SWTTestExt.configuredState.getEsb().name=esbName;
		SWTTestExt.configuredState.getEsb().version=TestConfigurator.esb.version;	

	}

}

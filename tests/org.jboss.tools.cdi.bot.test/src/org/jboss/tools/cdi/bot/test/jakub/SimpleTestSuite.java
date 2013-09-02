package org.jboss.tools.cdi.bot.test.jakub;

import org.jboss.tools.cdi.bot.test.AbstractTestSuite;
import org.jboss.tools.cdi.bot.test.named.NamedComponentsSearchingTest;
import org.jboss.tools.cdi.bot.test.openon.OpenOnTest;
import org.jboss.tools.ui.bot.ext.RequirementAwareSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(RequirementAwareSuite.class)
@SuiteClasses({
	//SimpleTest.class,
	//CDIWebProjectWizardTest.class,
//	WeldExtensionTest.class
//	CDIValidatorTest.class, // otestovat s AssignableDialogFilterTest
//	NamedRefactoringTest.class
//	AsYouTypeValidationTest.class,
//	AssignableDialogFilterTest.class
//	ProblemEligibleInjectionTest.class
//	NamedComponentsSearchingTest.class
	OpenOnTest.class
	
})
public class SimpleTestSuite extends AbstractTestSuite {

}

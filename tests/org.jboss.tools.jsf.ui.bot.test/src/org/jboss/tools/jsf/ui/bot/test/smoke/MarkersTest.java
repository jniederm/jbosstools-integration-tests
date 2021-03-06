/*******************************************************************************
 * Copyright (c) 2007-2011 Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributor:
 *     Red Hat, Inc. - initial API and implementation
 ******************************************************************************/
package org.jboss.tools.jsf.ui.bot.test.smoke;

import java.util.LinkedList;

import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.jboss.tools.jsf.ui.bot.test.JSFAutoTestCase;
import org.jboss.tools.ui.bot.ext.SWTJBTExt;
import org.jboss.tools.ui.bot.ext.SWTTestExt;
import org.jboss.tools.ui.bot.ext.Timing;
import org.jboss.tools.ui.bot.ext.helper.MarkerHelper;
import org.jboss.tools.ui.bot.ext.helper.QuickFixHelper;
import org.jboss.tools.ui.bot.ext.parts.SWTBotEditorExt;
import org.jboss.tools.ui.bot.ext.types.IDELabel;
import org.jboss.tools.vpe.ui.bot.test.VPEAutoTestCase;
/**
 * Test Markers position and attributes regarding to JSF components within jsp page
 * @author Vladimir Pakan
 *
 */
public class MarkersTest extends JSFAutoTestCase{
  private SWTBotEditorExt editor;
  private String originalEditorText;
  /**
   * Test open on functionality of JSF components within jsp page
   */
	public void testMarkers(){
	  MarkerHelper markerHelper = new MarkerHelper(TEST_PAGE,
	      VPEAutoTestCase.JBT_TEST_PROJECT_NAME,"WebContent","pages");
	  String textToSelect = "<h:outputText value=\"#{Message.header";
	  String insertText  = "yyaddedxx";
	  int[] expectedMarkerLines = new int[3];
	  String[] expectedMarkerDesc = new String[3];
    SWTJBTExt.selectTextInSourcePane(SWTTestExt.bot, 
        TEST_PAGE,
        textToSelect, 
        textToSelect.length(), 
        0, 
        0);
    editor.insertText(insertText);
    expectedMarkerLines[0] = editor.cursorPosition().line;
    expectedMarkerDesc[0] = "^\"header" + insertText + "\" cannot be resolved";
    textToSelect = "<h:outputText value=\"#{Message.prompt_message";
    SWTJBTExt.selectTextInSourcePane(SWTTestExt.bot,
        TEST_PAGE,
        textToSelect, 
        textToSelect.length(), 
        0, 
        0);
    editor.insertText(insertText);
    expectedMarkerLines[1] = editor.cursorPosition().line;
    expectedMarkerDesc[1] = "^\"prompt_message" + insertText + "\" cannot be resolved";
    textToSelect = "<h:inputText value=\"#{user.name";
    SWTJBTExt.selectTextInSourcePane(SWTTestExt.bot, 
        TEST_PAGE,
        textToSelect, 
        textToSelect.length(), 
        0, 
        0);
    editor.insertText(insertText);
    expectedMarkerLines[2] = editor.cursorPosition().line;
    expectedMarkerDesc[2] = "^\"name" + insertText + "\" cannot be resolved";
    editor.save();
    bot.sleep(Timing.time2S());
	  // Check markers
	  for (int index = 0 ; index < expectedMarkerLines.length ; index++){
	    markerHelper.checkForMarker(String.valueOf(expectedMarkerLines[index] + 1),
	        expectedMarkerDesc[index]);  
	  }	  
	  // check quick fix
	  LinkedList<String> expectedQuickFixes = new LinkedList<String>();
	  expectedQuickFixes.add("Configure Problem Severity for preference 'Property cannot be resolved'");
    QuickFixHelper.checkQuickFixContent(SWTTestExt.bot, 
        TEST_PAGE,
        textToSelect,
        textToSelect.length(),
        insertText.length(),
        expectedQuickFixes);
    editor.quickFix().useQuickFix(0);
    util.waitForShellWithTextIsFound(IDELabel.Shell.PREFERENCES_FILTERED, Timing.time3S());
    final SWTBot botProperties = bot.shell(IDELabel.Shell.PREFERENCES_FILTERED).activate().bot();
    String selectedTreeItemLabel = botProperties.tree().selection().get(0,0);
    boolean isFilterValueOK = false;
    final String expectedFilterValue = "Property cannot be resolved:";
    try{
      botProperties.text(expectedFilterValue);
      isFilterValueOK = true;
    } catch (WidgetNotFoundException wnfe){
      // do nothing
    }
    boolean isValidationOptionPresent = false;
    final String expectedValidationOption = "Property cannot be resolved:";
    try{
      botProperties.label(expectedValidationOption);
      isValidationOptionPresent = true;
    } catch (WidgetNotFoundException wnfe){
      // do nothing
    }
    bot.button(IDELabel.Button.CANCEL).click();
    final String expectedSelectedTreeItem = "Validation";
    assertTrue("Selected tree item within Preferences Dialog has to have label "
      +  expectedSelectedTreeItem 
      + " but is " + selectedTreeItemLabel,
      selectedTreeItemLabel.equals(expectedSelectedTreeItem));
    assertTrue("Validation Filter has to have value " + expectedFilterValue
        + " but it does not",
      isFilterValueOK);
    assertTrue("Validation Option has to have label " + expectedValidationOption
        + " but it does not",
      isValidationOptionPresent);
	}   
  @Override
  public void setUp() throws Exception {
    super.setUp();
    openPage(TEST_PAGE);
    editor = SWTTestExt.bot.swtBotEditorExtByTitle(TEST_PAGE);
    originalEditorText = editor.getText();
  }

  @Override
  public void tearDown() throws Exception {
    if (editor != null) {
      editor.setText(originalEditorText);
      editor.saveAndClose();
    }
    super.tearDown();
  }
}
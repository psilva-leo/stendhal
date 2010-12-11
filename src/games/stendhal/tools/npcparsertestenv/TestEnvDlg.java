/* $Id$ */
/***************************************************************************
 *                   (C) Copyright 2003-2010 - Stendhal                    *
 ***************************************************************************
 ***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/
package games.stendhal.tools.npcparsertestenv;

import games.stendhal.server.entity.npc.parser.CaseInsensitiveExprMatcher;
import games.stendhal.server.entity.npc.parser.ConversationParser;
import games.stendhal.server.entity.npc.parser.ExactExprMatcher;
import games.stendhal.server.entity.npc.parser.Expression;
import games.stendhal.server.entity.npc.parser.ExpressionMatcher;
import games.stendhal.server.entity.npc.parser.ExpressionType;
import games.stendhal.server.entity.npc.parser.JokerExprMatcher;
import games.stendhal.server.entity.npc.parser.Sentence;
import games.stendhal.server.entity.npc.parser.SimilarExprMatcher;
import games.stendhal.server.entity.npc.parser.TypeExprMatcher;

import java.awt.Cursor;
import java.io.IOException;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;

/**
 * Dialog of the NPC Conversation Parser Test Environment.
 * 
 * @author M. Fuchs
 */
@SuppressWarnings("serial")
public class TestEnvDlg extends javax.swing.JDialog {

	/** Creates new form TestEnvDlg */
	public TestEnvDlg() {
		initComponents();
		getRootPane().setDefaultButton(btMatch);
		lbUnknownWarning.setVisible(false);
	}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btMatch;
    private javax.swing.JButton btParse;
    private javax.swing.JComboBox cbMatchExpr;
    private javax.swing.JComboBox cbMatchType;
    private javax.swing.JComboBox cbSentence;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbAnalyzed;
    private javax.swing.JLabel lbMatchResult;
    private javax.swing.JLabel lbMatching;
    private javax.swing.JLabel lbNormalized;
    private javax.swing.JLabel lbNumeral;
    private javax.swing.JLabel lbObject;
    private javax.swing.JLabel lbSentence;
    private javax.swing.JLabel lbSubject;
    private javax.swing.JLabel lbToString;
    private javax.swing.JLabel lbTrigger;
    private javax.swing.JLabel lbTrimmed;
    private javax.swing.JLabel lbUnknownWarning;
    private javax.swing.JLabel lbVerb;
    private javax.swing.JPanel panelMatch;
    private javax.swing.JPanel panelSentence;
    private javax.swing.JTextField tfMatchToString;
    private javax.swing.JTextField tfNormalized;
    private javax.swing.JTextField tfNumeral;
    private javax.swing.JTextField tfObject;
    private javax.swing.JTextField tfSubject;
    private javax.swing.JTextField tfToString;
    private javax.swing.JTextField tfTrigger;
    private javax.swing.JTextField tfTrimmed;
    private javax.swing.JTextField tfVerb;
    private javax.swing.JTextPane tpExpressions;
    // End of variables declaration//GEN-END:variables

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed"
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        panelSentence = new javax.swing.JPanel();
        lbSentence = new javax.swing.JLabel();
        cbSentence = new javax.swing.JComboBox();
        btParse = new javax.swing.JButton();
        tfTrimmed = new javax.swing.JTextField();
        lbTrimmed = new javax.swing.JLabel();
        lbNormalized = new javax.swing.JLabel();
        tfNormalized = new javax.swing.JTextField();
        lbToString = new javax.swing.JLabel();
        tfToString = new javax.swing.JTextField();
        lbTrigger = new javax.swing.JLabel();
        tfTrigger = new javax.swing.JTextField();
        lbSubject = new javax.swing.JLabel();
        tfSubject = new javax.swing.JTextField();
        lbNumeral = new javax.swing.JLabel();
        tfNumeral = new javax.swing.JTextField();
        lbObject = new javax.swing.JLabel();
        tfObject = new javax.swing.JTextField();
        lbVerb = new javax.swing.JLabel();
        tfVerb = new javax.swing.JTextField();
        lbAnalyzed = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tpExpressions = new javax.swing.JTextPane();
        lbUnknownWarning = new javax.swing.JLabel();
        panelMatch = new javax.swing.JPanel();
        lbMatchResult = new javax.swing.JLabel();
        btMatch = new javax.swing.JButton();
        lbMatching = new javax.swing.JLabel();
        cbMatchType = new javax.swing.JComboBox();
        cbMatchExpr = new javax.swing.JComboBox();
        tfMatchToString = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("NPC Parser Test Environment");
        setBounds(new java.awt.Rectangle(49, 200, 0, 0));
        setLocation(new java.awt.Point(200, 200));
        setPreferredSize(new java.awt.Dimension(665, 680));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        panelSentence.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelSentence.setPreferredSize(new java.awt.Dimension(649, 480));

        lbSentence.setText("Please enter a sentence:");

        cbSentence.setEditable(true);
        cbSentence.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "buy 3 cookies", "buy seven bananas", "give a bottle of wine", "buy enhanced lion shield", "Would you like to have an ice cream?", "Mary has a little lamb.", "I and you, he and they", "What is the an answer to life, the universe and everything?", "to be or not to be", "Take these three grilled steaks and have fun!", "99 red balloons", "Hi, how are you?", "_Hi, how are you?" }));
        cbSentence.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbSentenceActionPerformed(evt);
            }
        });

        btParse.setText("parse");
        btParse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btParseActionPerformed(evt);
            }
        });

        tfTrimmed.setEditable(false);

        lbTrimmed.setText("Normalized:");

        lbNormalized.setText("Trimmed:");

        tfNormalized.setEditable(false);

        lbToString.setText("toString():");

        tfToString.setEditable(false);

        lbTrigger.setText("Trigger:");

        tfTrigger.setEditable(false);

        lbSubject.setText("Subject:");

        tfSubject.setEditable(false);

        lbNumeral.setText("Numeral:");

        tfNumeral.setEditable(false);

        lbObject.setText("Object:");

        tfObject.setEditable(false);

        lbVerb.setText("Verb:");

        tfVerb.setEditable(false);

        lbAnalyzed.setText("Expressions:");

        tpExpressions.setEditable(false);
        jScrollPane1.setViewportView(tpExpressions);

        lbUnknownWarning.setForeground(java.awt.Color.red);
        lbUnknownWarning.setText("Please add an entry for all UNKNOWN words in red expressions to the NPC parser word list!");

        org.jdesktop.layout.GroupLayout panelSentenceLayout = new org.jdesktop.layout.GroupLayout(panelSentence);
        panelSentence.setLayout(panelSentenceLayout);
        panelSentenceLayout.setHorizontalGroup(
            panelSentenceLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panelSentenceLayout.createSequentialGroup()
                .addContainerGap()
                .add(panelSentenceLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(panelSentenceLayout.createSequentialGroup()
                        .add(panelSentenceLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(lbNormalized)
                            .add(lbSentence)
                            .add(lbTrimmed)
                            .add(lbToString)
                            .add(cbSentence, 0, 525, Short.MAX_VALUE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(btParse))
                    .add(tfTrimmed, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 609, Short.MAX_VALUE)
                    .add(tfNormalized, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 609, Short.MAX_VALUE)
                    .add(tfToString, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 609, Short.MAX_VALUE))
                .addContainerGap())
            .add(panelSentenceLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(panelSentenceLayout.createSequentialGroup()
                    .addContainerGap()
                    .add(panelSentenceLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(panelSentenceLayout.createSequentialGroup()
                            .add(panelSentenceLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 243, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(lbAnalyzed))
                            .add(27, 27, 27)
                            .add(panelSentenceLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                .add(lbSubject)
                                .add(lbVerb)
                                .add(lbNumeral)
                                .add(lbObject)
                                .add(lbTrigger))
                            .add(18, 18, 18)
                            .add(panelSentenceLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                .add(tfSubject, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE)
                                .add(tfVerb, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE)
                                .add(tfNumeral, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE)
                                .add(tfObject, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE)
                                .add(tfTrigger, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE)))
                        .add(lbUnknownWarning))
                    .addContainerGap()))
        );
        panelSentenceLayout.setVerticalGroup(
            panelSentenceLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panelSentenceLayout.createSequentialGroup()
                .addContainerGap()
                .add(lbSentence)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(panelSentenceLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(cbSentence, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(btParse))
                .add(10, 10, 10)
                .add(lbNormalized)
                .add(2, 2, 2)
                .add(tfNormalized, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(lbTrimmed)
                .add(2, 2, 2)
                .add(tfTrimmed, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(lbToString)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(tfToString, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(226, Short.MAX_VALUE))
            .add(panelSentenceLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(panelSentenceLayout.createSequentialGroup()
                    .add(264, 264, 264)
                    .add(panelSentenceLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(panelSentenceLayout.createSequentialGroup()
                            .add(lbAnalyzed)
                            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                            .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 126, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .add(panelSentenceLayout.createSequentialGroup()
                            .add(panelSentenceLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                .add(tfSubject, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(lbSubject))
                            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                            .add(panelSentenceLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                .add(tfVerb, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(lbVerb))
                            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                            .add(panelSentenceLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                .add(tfNumeral, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(lbNumeral))
                            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                            .add(panelSentenceLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                .add(tfObject, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(lbObject))
                            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                            .add(panelSentenceLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                .add(tfTrigger, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(lbTrigger))))
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(lbUnknownWarning)
                    .addContainerGap(24, Short.MAX_VALUE)))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 4;
        gridBagConstraints.ipady = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
        getContentPane().add(panelSentence, gridBagConstraints);

        panelMatch.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelMatch.setPreferredSize(new java.awt.Dimension(653, 100));

        lbMatchResult.setText(" ");

        btMatch.setText("test match");
        btMatch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btMatchActionPerformed(evt);
            }
        });

        lbMatching.setText("Please select matching type and enter a matching expression:");

        cbMatchType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "standard matching", "joker matching", "exact matching", "case insensitive", "similarity matching", "type expression" }));
        cbMatchType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbMatchTypeActionPerformed(evt);
            }
        });

        cbMatchExpr.setEditable(true);
        cbMatchExpr.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "buy 3 cookies", "buy seven bananas", "buy bananas", "buy * bananas", "give a bottle of wine", "give *", "buy enhanced lion shield", "Would you like to have an ice cream?", "Mary has a little lamb.", "I and you, he and they", "What is the an answer to life, the universe and everything?", "to be or not to be", "Take these three grilled steaks and have fun!", "99 red balloons", "Hi, how are you?", "_Hi, how are you?" }));
        cbMatchExpr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbMatchExprActionPerformed(evt);
            }
        });

        tfMatchToString.setEditable(false);

        org.jdesktop.layout.GroupLayout panelMatchLayout = new org.jdesktop.layout.GroupLayout(panelMatch);
        panelMatch.setLayout(panelMatchLayout);
        panelMatchLayout.setHorizontalGroup(
            panelMatchLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panelMatchLayout.createSequentialGroup()
                .addContainerGap()
                .add(panelMatchLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(panelMatchLayout.createSequentialGroup()
                        .add(cbMatchType, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(cbMatchExpr, 0, 432, Short.MAX_VALUE)
                        .addContainerGap())
                    .add(panelMatchLayout.createSequentialGroup()
                        .add(tfMatchToString, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 621, Short.MAX_VALUE)
                        .add(8, 8, 8))
                    .add(panelMatchLayout.createSequentialGroup()
                        .add(lbMatching)
                        .addContainerGap(241, Short.MAX_VALUE))
                    .add(panelMatchLayout.createSequentialGroup()
                        .add(btMatch)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(lbMatchResult, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 509, Short.MAX_VALUE))))
        );
        panelMatchLayout.setVerticalGroup(
            panelMatchLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panelMatchLayout.createSequentialGroup()
                .add(lbMatching)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(panelMatchLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(cbMatchType, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(cbMatchExpr, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(tfMatchToString, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 11, Short.MAX_VALUE)
                .add(panelMatchLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(btMatch)
                    .add(lbMatchResult))
                .add(135, 135, 135))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 4;
        gridBagConstraints.ipady = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
        getContentPane().add(panelMatch, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

	private void btParseActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btParseActionPerformed
		updateParsed();
	}// GEN-LAST:event_btParseActionPerformed

	private void cbSentenceActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cbSentenceActionPerformed
//		updateParsed();
		updateMatching();
	}// GEN-LAST:event_cbSentenceActionPerformed

	private String updateParsed() {
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

		Object selected = cbSentence.getSelectedItem().toString();
		String text = selected != null ? selected.toString() : "";
		processSentence(text);

		setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

		return text;
	}

	private ExpressionMatcher matcher = new ExpressionMatcher();

	private void cbMatchExprActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cbMatchExprActionPerformed
		updateMatching();
	}// GEN-LAST:event_cbMatchExprActionPerformed

	private void btMatchActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btMatchActionPerformed
		updateMatching();
	}// GEN-LAST:event_btMatchActionPerformed

	private void cbMatchTypeActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cbMatchTypeActionPerformed
		updateMatching();
	}// GEN-LAST:event_cbMatchTypeActionPerformed

	private void updateMatching() {
		Object sel = cbMatchType.getSelectedItem();

		if (sel.equals("standard matching")) {
			matcher = new ExpressionMatcher();
		} else if (sel.equals("joker matching")) {
			matcher = new JokerExprMatcher();
		} else if (sel.equals("exact matching")) {
			matcher = new ExactExprMatcher();
		} else if (sel.equals("case insensitive")) {
			matcher = new CaseInsensitiveExprMatcher();
		} else if (sel.equals("similarity matching")) {
			matcher = new SimilarExprMatcher();
		} else if (sel.equals("type expression")) {
			matcher = new TypeExprMatcher();
		}

		String text = updateParsed();

		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

		Object matchSel = cbMatchExpr.getSelectedItem().toString();
		processMatching(text, matchSel != null ? matchSel.toString() : "");

		setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String args[]) {
		// java.awt.EventQueue.invokeLater(new Runnable() {
		// public void run() {
		new TestEnvDlg().setVisible(true);
		// }
		// });
	}

	private static final String initialHtml = "<html><head>"
			+ "<title>An example HTMLDocument</title>"
			// + "<style type=\"text/css\"> ul {color: red;}</style>"
			+ "</head>" + "<body><p></p></body></html>";

	private HTMLDocument initHtml(JTextPane p) {
		p.setContentType("text/html");
		p.setText(initialHtml);

		return (HTMLDocument) tpExpressions.getDocument();
	}

	private void addHtml(HTMLDocument d, String html) {
		try {
			d.insertBeforeEnd(d.getDefaultRootElement(), html);
		} catch (BadLocationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String decodeExprType(ExpressionType t) {
		StringBuilder type = new StringBuilder();

		if (t.isEmpty())
			type.append(" - UNKNOWN");
		else {
			if (t.isSubject())
				type.append(" - subject");

			if (t.isObject())
				type.append(" - object");

			if (t.isName())
				type.append(" - name");

			if (t.isAnimal())
				type.append(" - animal");

			if (t.isFood())
				type.append(" - food");

			if (t.isFluid())
				type.append(" - fluid");

			if (t.isVerb())
				type.append(" - verb");

			if (t.isGerund())
				type.append(" - gerund");

			if (t.isNumeral())
				type.append(" - numeral");

			if (t.isAdjective())
				type.append(" - adjective");

			if (t.isPlural())
				type.append(" - plural");

			if (t.isIgnore())
				type.append(" - ignore");

			if (t.isPreposition())
				type.append(" - preposition");

			if (t.isPronoun())
				type.append(" - pronoun");

			if (t.hasQuestion())
				type.append(" - question word");

			if (t.isObsessional())
				type.append(" - obsessional");

			if (t.hasColor())
				type.append(" - color");

			if (t.isConditional())
				type.append(" - conditional");

			if (t.isNegated())
				type.append(" - negated");

			if (t.isDynamic())
				type.append(" - DYNAMIC");
		}

		return type.toString();
	}

	private void processSentence(String text) {
		Sentence sentence = ConversationParser.parse(text);

		tfTrimmed.setText(sentence.getTrimmedText());

		tfNormalized.setText(sentence.getNormalized());

		tfToString.setText(sentence.toString());

		tfTrigger.setText(sentence.getTriggerExpression().toString());

		String subj = sentence.getSubjectName();
		tfSubject.setText(subj != null ? subj : "["
				+ sentence.getSubjectCount() + " subjects]");

		String verb = sentence.getVerbString();
		tfVerb.setText(verb != null ? verb : "[" + sentence.getVerbCount()
				+ " verbs]");

		Expression num = sentence.getNumeral();
		tfNumeral.setText(num != null ? num.getNormalized() : "["
				+ sentence.getNumeralCount() + " numerals]");

		String obj = sentence.getObjectName();
		tfObject.setText(obj != null ? obj : "[" + sentence.getObjectCount()
				+ " objects]");

		HTMLDocument d = initHtml(tpExpressions);

		int unknown = 0;
		for (Expression e : sentence) {
			String style = "";
			ExpressionType t = e.getType();

			if (t.isEmpty()) {
				style = " style=\"color:red\"";
				++unknown;
			}

			String type = decodeExprType(t);

			addHtml(d, "<div" + style + ">" + e.getOriginal() + type + "</div>");
		}

		tpExpressions.setDocument(d);

		lbUnknownWarning.setVisible(unknown > 0);
	}

	private void processMatching(String text, String matchText) {
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

		Sentence matchSentence = ConversationParser.parse(matchText, matcher);
		tfMatchToString.setText(matchSentence.toString());

		Sentence sentence = ConversationParser.parse(text, matcher);
		boolean matches = sentence.matchesFull(matchSentence);
		lbMatchResult
				.setText(matches ? "-> The user input MATCHES the match expression!"
						: "-> User input and match expression don't match.");

		setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}
}

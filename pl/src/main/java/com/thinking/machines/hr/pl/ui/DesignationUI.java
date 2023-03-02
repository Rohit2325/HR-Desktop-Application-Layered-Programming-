package com.thinking.machines.hr.pl.ui;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.pl.model.*;
//import javax.swing.filechooser.FileFilter.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.awt.Rectangle.*;
import java.io.*;
public class DesignationUI extends JFrame implements DocumentListener,ListSelectionListener
{
private JLabel titleLabel;
private JLabel searchLabel;
private JTextField searchTextField;
private JLabel searchErrorLabel;
private JButton clearSearchTextFieldButton;
private JTable designationTable;
private JScrollPane scrollPane;
private Container container;
private DesignationModel designationModel; 
private DesignationPanel designationPanel;
private enum MODE{VIEW,ADD,EDIT,DELETE,EXPORT_TO_PDF};
private MODE mode;
ImageIcon logoIcon = new ImageIcon();
ImageIcon editIcon = new ImageIcon();
public DesignationUI()
{
super("Thinking machines");
initComponent();
setAppearance();
addListeners();
//set();
designationPanel.setViewMode();
setDefaultCloseOperation(EXIT_ON_CLOSE);
}

//*********************************************

public void initComponent()
{
ImageIcon logoIcon = new ImageIcon("C:"+File.separator+"javaprojects"+File.separator+"hr"+File.separator+"Icons"+File.separator+"logoIcon.png");
setIconImage(logoIcon.getImage());
editIcon = new ImageIcon("C:"+File.separator+"javaprojects"+File.separator+"hr"+File.separator+"Icons"+File.separator+"add_Icon.png");
designationModel = new DesignationModel();
titleLabel = new JLabel("Designations");
searchLabel = new JLabel("search");
searchTextField = new JTextField();
searchErrorLabel = new JLabel("");
clearSearchTextFieldButton = new JButton("x");
designationTable = new JTable(designationModel);
scrollPane = new JScrollPane(designationTable,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
designationPanel = new DesignationPanel();
container = getContentPane();
}//initComponentsEnds;


public void setAppearance()
{
Font titleFont = new Font("Verdana",Font.BOLD,18);
Font searchFont = new Font("Verdana",Font.BOLD,16);
Font dataFont = new Font("Verdana",Font.PLAIN,16);
Font searchErrorFont = new Font("Verdana",Font.BOLD,12);
Font columnHeaderFont = new Font("Verdana",Font.BOLD,12);
titleLabel.setFont(titleFont);
searchLabel.setFont(searchFont);
designationTable.setFont(dataFont);
searchErrorLabel.setFont(searchErrorFont);
searchErrorLabel.setForeground(Color.red);
designationTable.setRowHeight(30);
designationTable.setRowSelectionAllowed(true);
designationTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
designationTable.getColumnModel().getColumn(0).setPreferredWidth(20);
designationTable.getColumnModel().getColumn(1).setPreferredWidth(400);
JTableHeader header = designationTable.getTableHeader();
header.setFont(columnHeaderFont);
header.setReorderingAllowed(false);
header.setResizingAllowed(false);
container.setLayout(null);
int lm,tm;
lm=0;
tm=0;
titleLabel.setBounds(lm+10,tm+10,200,40);
searchErrorLabel.setBounds(lm+10+400-5,tm+10+40-25,70,30);
searchLabel.setBounds(lm+10,tm+10+40,100,30);
searchTextField.setBounds(lm+10+65,tm+10+40,400,30);
clearSearchTextFieldButton.setBounds(lm+10+65+400+5,tm+10+40,30,30);
scrollPane.setBounds(lm+10,tm+10+40+30+5,495,300);
designationPanel.setBounds(lm+10,tm+10+40+30+5+300+5,495,160);
container.add(titleLabel);
container.add(searchErrorLabel);
container.add(searchLabel);
container.add(searchTextField);
container.add(clearSearchTextFieldButton);
container.add(scrollPane);
container.add(designationPanel);
int w,h;
w=530;
h=600;
setSize(w,h);
Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
w = (d.width/2)-(w/2);
h = (d.height/2)-(h/2); 
setLocation(w,h);
}//setAppearanceEnds


public void addListeners()
{
searchTextField.getDocument().addDocumentListener(this);
clearSearchTextFieldButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
searchTextField.setText("");
searchTextField.requestFocus();
}
});
designationTable.getSelectionModel().addListSelectionListener(this);
}//addListenersEnds;


public void searchDesignations()
{
searchErrorLabel.setText("");
String title;
title = searchTextField.getText().trim();
if(title.length()==0) return;
try
{
int rowIndex = designationModel.indexOfTitle(title,true);
designationTable.setRowSelectionInterval(rowIndex,rowIndex);
Rectangle rectangle = designationTable.getCellRect(rowIndex,0,true);
designationTable.scrollRectToVisible(rectangle);
}catch(BLException blException)
{
searchErrorLabel.setText("Not Found");
}
}//searchDesignationsEnds;



public void changedUpdate(DocumentEvent de)
{
searchDesignations();
}

public void removeUpdate(DocumentEvent de)
{
searchDesignations();
}

public void insertUpdate(DocumentEvent de)
{
searchDesignations();
}

public void valueChanged(ListSelectionEvent lv)
{
int rowIndex = designationTable.getSelectedRow();
try
{
DesignationInterface designation;
designation = designationModel.getDesignationAt(rowIndex);
designationPanel.setDesignation(designation);
}catch(BLException blException)
{
designationPanel.clearDesignation();
}
}

private void setViewMode()
{
this.mode = MODE.VIEW;
if(designationModel.getRowCount()==0)
{
searchTextField.setEnabled(false);
clearSearchTextFieldButton.setEnabled(false);
designationTable.setEnabled(false);
}
else
{
searchTextField.setEnabled(true);
clearSearchTextFieldButton.setEnabled(true);
designationTable.setEnabled(true);
}
}

private void setAddMode()
{
this.mode = MODE.ADD;
searchTextField.setEnabled(false);
clearSearchTextFieldButton.setEnabled(false);
designationTable.setEnabled(false);
}

private void setEditMode()
{
this.mode = MODE.EDIT;
searchTextField.setEnabled(false);
clearSearchTextFieldButton.setEnabled(false);
designationTable.setEnabled(false);
}

private void setDeleteMode()
{
this.mode = MODE.DELETE;
searchTextField.setEnabled(false);
clearSearchTextFieldButton.setEnabled(false);
designationTable.setEnabled(false);
}

private void setExportToPDFMode()
{
this.mode = MODE.EXPORT_TO_PDF;
searchTextField.setEnabled(false);
clearSearchTextFieldButton.setEnabled(false);
designationTable.setEnabled(false);
}



//inner class Starts
class DesignationPanel extends JPanel
{
private JLabel titleCaptionLabel;
private JLabel titleLabel;
private JTextField titleTextField;
private JButton clearTitleTextFieldButton;
private JButton addButton;
private JButton editButton;
private JButton cancelButton;
private JButton deleteButton;
private JButton exportToPDFButton;
private JPanel buttonsPanel;
private DesignationInterface designation;
private ImageIcon addIcon;
DesignationPanel()
{
setBorder(BorderFactory.createLineBorder(new Color(150,150,150)));
initComponents();
setAppearance();
addListeners();
}
public void initComponents()
{
titleCaptionLabel = new JLabel("Designation");
titleLabel = new JLabel("");
titleTextField = new JTextField();
clearTitleTextFieldButton = new JButton("x");
addButton = new JButton("A");
editButton = new JButton("E");
cancelButton = new JButton("C");
deleteButton = new JButton("D");
exportToPDFButton = new JButton("pdf");
buttonsPanel = new JPanel();
addIcon = new ImageIcon("C:"+File.separator+"javaprojects"+File.separator+"hr"+File.separator+"Icons"+File.separator+"add_Icon.png");
}

public void setDesignation(DesignationInterface designation)
{
this.designation = designation;
titleLabel.setText(designation.getTitle());
}

public void clearDesignation()
{
this.designation = null;
titleLabel.setText("");
}

public void setAppearance()
{
Font captionFont = new Font("Verdana",Font.BOLD,16);
Font dataFont = new Font("Verdana",Font.PLAIN,16);
titleCaptionLabel.setFont(captionFont);
titleLabel.setFont(dataFont);
titleTextField.setFont(dataFont);
setLayout(null);
int lm,tm;
lm=0;
tm=0;
titleCaptionLabel.setBounds(lm+10,tm+15,110,30);
titleLabel.setBounds(lm+10+200,tm+15,100,30);
titleTextField.setBounds(lm+10+100+20,tm+15,250,30);
clearTitleTextFieldButton.setBounds(130+320,tm+15,30,25);
buttonsPanel.setBounds(lm+10+30+5,tm+20+30+20,400,70);
buttonsPanel.setBorder(BorderFactory.createLineBorder(new Color(165,165,165)));
addButton.setBounds(45,10,45,45);
editButton.setBounds(45+45+15,10,45,45);
cancelButton.setBounds(165,10,45,45);
deleteButton.setBounds(225,10,45,45);
exportToPDFButton.setBounds(285,10,45,45);
buttonsPanel.setLayout(null);
add(titleCaptionLabel);
add(titleTextField);
add(titleLabel);
add(clearTitleTextFieldButton);
buttonsPanel.add(addButton);
buttonsPanel.add(editButton);
buttonsPanel.add(cancelButton);
buttonsPanel.add(deleteButton);
buttonsPanel.add(exportToPDFButton);
add(buttonsPanel);
}

public boolean addDesignation()
{
String title = titleTextField.getText().trim();
if(title.length()==0)
{
JOptionPane.showMessageDialog(this,"Designation Required");
return false;
}
try
{
DesignationInterface d = new Designation();
d.setTitle(title);
designationModel.add(d);
try
{
int rowIndex = designationModel.indexOfDesignation(d);
designationTable.setRowSelectionInterval(rowIndex,rowIndex);
Rectangle rectangle = designationTable.getCellRect(rowIndex,0,true);
designationTable.scrollRectToVisible(rectangle);
}catch(BLException blException)
{
//do nothing
}
}catch(BLException blException)
{
if(blException.hasException("title"))
{
JOptionPane.showMessageDialog(this,blException.getException("title"));
return false;
}
if(blException.hasGenericException())
{
JOptionPane.showMessageDialog(this,blException.getGenericException());
return false;
}
}
return true;
}//addDesignation Ends;


public boolean updateDesignation()
{
String title = titleTextField.getText().trim();
if(title.length()==0)
{
JOptionPane.showMessageDialog(this,"Designation Required");
return false;
}
try
{
DesignationInterface d = new Designation();
d.setCode(this.designation.getCode());
d.setTitle(title);
designationModel.update(d);
try
{
int rowIndex = designationModel.indexOfDesignation(d);
designationTable.setRowSelectionInterval(rowIndex,rowIndex);
Rectangle rectangle = designationTable.getCellRect(rowIndex,0,true);
designationTable.scrollRectToVisible(rectangle);
}catch(BLException blException)
{
//do nothing
}
}catch(BLException blException)
{
if(blException.hasException("title"))
{
JOptionPane.showMessageDialog(this,blException.getException("title"));
return false;
}
if(blException.hasGenericException())
{
JOptionPane.showMessageDialog(this,blException.getGenericException());
return false;
}
}
return true;
}//updateDesignationEnds;


private void removeDesignation()
{
String title = this.designation.getTitle();
try
{
designationModel.remove(this.designation.getCode());
JOptionPane.showMessageDialog(this,title+ " deleted");
}catch(BLException blException)
{
if(blException.hasGenericException())
{
JOptionPane.showMessageDialog(this,blException.getGenericException());
}
if(blException.hasException("code"))
{
JOptionPane.showMessageDialog(this,blException.getException("code"));
}
}
}//removeDesignation ends;


public void addListeners()
{
this.addButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
if(mode==MODE.VIEW)
{
setAddMode();
}
else
{
if(addDesignation())
{
setViewMode();
}
}
}
});

editButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
if(mode==MODE.VIEW)
{
setEditMode();
}
else
{
if(updateDesignation())
{
setViewMode();
}
}
}
});


this.cancelButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
setViewMode();
}
});

this.clearTitleTextFieldButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
titleTextField.setText("");
titleTextField.requestFocus();
}
});

this.deleteButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
if(mode==MODE.VIEW)
{
setDeleteMode();
}
setViewMode();
}
});

this.exportToPDFButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
JFileChooser jfc = new JFileChooser();
jfc.setCurrentDirectory(new File("."));
int selectedOption =jfc.showSaveDialog(DesignationUI.this);
if(selectedOption==JFileChooser.APPROVE_OPTION);
{
try
{
File selectedFile = jfc.getSelectedFile();
String pdfFile = selectedFile.getAbsolutePath();
if(pdfFile.endsWith(".")) pdfFile+= "pdf";
if(pdfFile.endsWith(".pdf")==false) pdfFile+= ".pdf";
File file = new File(pdfFile);
File parent = new File(file.getParent());
if(parent.exists()==false || parent.isDirectory()==false)
{
JOptionPane.showMessageDialog(DesignationUI.this,"Incorrect Path "+file.getAbsolutePath());
return;
}
designationModel.exportToPDF(file);
JOptionPane.showMessageDialog(DesignationUI.this,"Data Exported to "+file.getAbsolutePath());
}catch(BLException blException)
{
if(blException.hasGenericException())
{
JOptionPane.showMessageDialog(DesignationUI.this,blException.getGenericException());
return;
}
}
catch(Exception e)
{
System.out.println(e.getMessage());
}
}//ifblock ends

jfc.addChoosableFileFilter(new javax.swing.filechooser.FileFilter(){
public boolean accept(File file)
{
//if(file.isDirectory()) return true;
if(file.getName().endsWith(".pdf")) return true;
return false;
}
public String getDescription()
{
return ".pdf";
}
});

}//ActionPeformed ends;
});




}//addListener ends;


void setViewMode()
{
DesignationUI.this.setViewMode();
this.addButton.setText("A");

this.editButton.setText("E");
this.titleLabel.setVisible(true);
this.titleTextField.setVisible(false);
this.clearTitleTextFieldButton.setVisible(false);
if(designationModel.getRowCount()==0)
{
this.addButton.setEnabled(false);
this.editButton.setEnabled(false);
this.cancelButton.setEnabled(false);
this.deleteButton.setEnabled(false);
this.exportToPDFButton.setEnabled(false);
}
else
{
this.addButton.setEnabled(true);
this.editButton.setEnabled(true);
this.cancelButton.setEnabled(false);
this.deleteButton.setEnabled(true);
this.exportToPDFButton.setEnabled(true);
}
}

void setAddMode()
{
DesignationUI.this.setAddMode();
titleTextField.setText("");
titleLabel.setVisible(false);
titleTextField.setVisible(true);
titleTextField.requestFocus();
this.clearTitleTextFieldButton.setVisible(true);
addButton.setText("S");
editButton.setEnabled(false);
cancelButton.setEnabled(true);
deleteButton.setEnabled(false);
exportToPDFButton.setEnabled(false);
}

void setEditMode()
{
if(designationTable.getSelectedRow()<0 || designationTable.getSelectedRow()>designationModel.getRowCount())
{
JOptionPane.showMessageDialog(this,"Select designation to edit");
return;
}
DesignationUI.this.setEditMode();
titleTextField.setText(this.designation.getTitle());
titleLabel.setVisible(false);
titleTextField.setVisible(true);
this.clearTitleTextFieldButton.setVisible(true);
addButton.setEnabled(false);
cancelButton.setEnabled(true);
deleteButton.setEnabled(false);
exportToPDFButton.setEnabled(false);
editButton.setText("U");
}

void setDeleteMode()
{
if(designationTable.getSelectedRow()<0 || designationTable.getSelectedRow()>designationModel.getRowCount())
{
JOptionPane.showMessageDialog(this,"Select designation to delete");
return;
}
DesignationUI.this.setDeleteMode();
addButton.setEnabled(false);
editButton.setEnabled(false);
deleteButton.setEnabled(false);
cancelButton.setEnabled(false);
exportToPDFButton.setEnabled(false);
int optionSelection=JOptionPane.showConfirmDialog(this,this.designation.getTitle()+" deleted ?","Cofirmation",JOptionPane.YES_NO_OPTION);
if(optionSelection==JOptionPane.NO_OPTION) return;
removeDesignation();
setViewMode();
}

void setExportToPDFMode()
{
DesignationUI.this.setExportToPDFMode();
addButton.setEnabled(false);
editButton.setEnabled(false);
deleteButton.setEnabled(false);
cancelButton.setEnabled(false);
exportToPDFButton.setEnabled(false);
}


}//innerClassEnds;
}//DesignationUI class ends
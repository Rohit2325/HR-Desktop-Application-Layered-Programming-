import com.thinking.machines.hr.pl.model.*;
import javax.swing.*;
import java.awt.*;
class DesignationModelAddTestCase extends JFrame
{
private JTable table;
private JScrollPane jsp;
private Container container;
DesignationModel designationModel;
DesignationModelAddTestCase()
{
designationModel = new DesignationModel();
table = new JTable(designationModel);
jsp = new JScrollPane(table,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
container = getContentPane();
container.setLayout(new FlowLayout());
container.add(jsp);
setLocation(600,600);
setSize(500,300);
setVisible(true);
setDefaultCloseOperation(EXIT_ON_CLOSE);
}
public static void main(String gg[])
{
DesignationModelAddTestCase d = new DesignationModelAddTestCase();
}
}
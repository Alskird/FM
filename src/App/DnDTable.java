package App;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DnDTable extends TransferHandler {

    private Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    private DataFlavor flavor = DataFlavor.javaFileListFlavor;
    private FM fmDnD;
    DnDTable(FM fm){
        this.fmDnD = fm;
    }
    @Override
    public int getSourceActions(JComponent c) {
        System.out.println(COPY_OR_MOVE);
        return COPY_OR_MOVE;
    }

    @Override
    protected Transferable createTransferable(JComponent c) {
        JTable tableDnD = (JTable) c;
        System.out.println(tableDnD.getValueAt(tableDnD.getSelectedRow(),tableDnD.getSelectedColumn()).toString());
        fmDnD.copyFileOrFolder(tableDnD.getValueAt(tableDnD.getSelectedRow(),tableDnD.getSelectedColumn()).toString());
        clipboard = fmDnD.getClipboard();
        if (clipboard.isDataFlavorAvailable(flavor)) {
            try {
                List list = (List) clipboard.getData(flavor);
                Iterator countriesIterator = list.iterator();
/*                for (int i = 0; i < list.size(); i++) {
                    System.out.println("Данные буфера");
                    System.out.println(list.get(i));
                    System.out.println("-------------------------------");
                }*/
                while (countriesIterator.hasNext()) {
                    //System.out.println(countriesIterator.next().toString());
                }
            } catch (UnsupportedFlavorException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return new StringSelection(tableDnD.getValueAt(tableDnD.getSelectedRow(),tableDnD.getSelectedColumn()).toString());
    }
}

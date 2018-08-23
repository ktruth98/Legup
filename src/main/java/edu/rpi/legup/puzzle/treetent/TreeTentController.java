package edu.rpi.legup.puzzle.treetent;

import edu.rpi.legup.app.GameBoardFacade;
import edu.rpi.legup.controller.ElementController;
import edu.rpi.legup.history.EditDataCommand;
import edu.rpi.legup.history.ICommand;
import edu.rpi.legup.model.gameboard.PuzzleElement;
import edu.rpi.legup.ui.boardview.BoardView;
import edu.rpi.legup.ui.treeview.TreePanel;
import edu.rpi.legup.ui.treeview.TreeView;
import edu.rpi.legup.ui.treeview.TreeViewSelection;

import java.awt.event.MouseEvent;

import static edu.rpi.legup.app.GameBoardFacade.getInstance;

public class TreeTentController extends ElementController
{

    private TreeTentElementView lastCellPressed;
    private TreeTentElementView dragStart;
    public TreeTentController()
    {
        super();
        this.dragStart = null;
        this.lastCellPressed = null;
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        BoardView boardView = getInstance().getLegupUI().getBoardView();
        dragStart = (TreeTentElementView) boardView.getElement(e.getPoint());
        lastCellPressed = (TreeTentElementView) boardView.getElement(e.getPoint());
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        TreePanel treePanel = GameBoardFacade.getInstance().getLegupUI().getTreePanel();
        TreeTentElementView dragEnd = (TreeTentElementView) boardView.getElement(e.getPoint());
        TreeView treeView = GameBoardFacade.getInstance().getLegupUI().getTreePanel().getTreeView();
        BoardView boardView = getInstance().getLegupUI().getBoardView();
        TreeTentBoard board = (TreeTentBoard)getInstance().getBoard();
        lastCellPressed = (TreeTentElementView) boardView.getElement(e.getPoint());
        TreeViewSelection selection = treeView.getSelection();
        if(dragStart != null && dragStart == lastCellPressed) {
            ICommand edit = new EditDataCommand(lastCellPressed, selection, e);
            if(edit.canExecute())
            {
                edit.execute();
                getInstance().getHistory().pushChange(edit);
                treePanel.updateError("");
            }
            else
            {
                treePanel.updateError(edit.getExecutionError());
            }
        } else if (dragStart != null && lastCellPressed != null) {
            ICommand editLine = new EditLineCommand(dragStart, lastCellPressed, selection);
            if (editLine.canExecute()) {
                editLine.execute();
                getInstance().getHistory().pushChange(editLine);
            } else {
                treePanel.updateError(editLine.getExecutionError());
            }
        }
        dragStart = null;
        lastCellPressed = null;
    }

    @Override
    public void changeCell(MouseEvent e, PuzzleElement data)
    {
        TreeTentCell cell = (TreeTentCell)data;
        if(e.getButton() == MouseEvent.BUTTON1)
        {
            if(e.isControlDown())
            {
                this.boardView.getSelectionPopupMenu().show(boardView, this.boardView.getCanvas().getX() + e.getX(), this.boardView.getCanvas().getY() + e.getY());
            }
            else
            {
                if(cell.getData() == 0)
                {
                    data.setData(2);
                }
                else if(cell.getData() == 2)
                {
                    data.setData(3);
                }
                else
                {
                    data.setData(0);
                }
            }
        }
        else if(e.getButton() == MouseEvent.BUTTON3)
        {
            if(cell.getData() == 0)
            {
                data.setData(3);
            }
            else if(cell.getData() == 2)
            {
                data.setData(0);
            }
            else
            {
                data.setData(2);
            }
        }
    }
}

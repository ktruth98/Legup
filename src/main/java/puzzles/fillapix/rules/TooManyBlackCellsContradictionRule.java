package puzzles.fillapix.rules;

import model.gameboard.Board;
import model.rules.ContradictionRule;
import puzzles.fillapix.CellState;
import puzzles.fillapix.FillapixBoard;
import puzzles.fillapix.FillapixCell;

public class TooManyBlackCellsContradictionRule extends ContradictionRule
{

    public TooManyBlackCellsContradictionRule()
    {
        super("Too Many Black Cells",
                "There may not be more black cells than the number",
                "images/fillapix/contradictions/TooManyBlackCells.png");
    }

    @Override
    public String checkContradiction(Board board)
    {
        FillapixBoard fillapixBoard = (FillapixBoard) board;
        int rowSize = fillapixBoard.getWidth();
        int colSize = fillapixBoard.getHeight();
        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < colSize; j++) {
                FillapixCell cell = fillapixBoard.getCell(i,j);
                if (cell.getValueInt() != -1) {
                    int numBlackCells = ((FillapixBoard) board).getNumCells(cell, CellState.BLACK);
                    if (numBlackCells > cell.getValueInt()) {
                        return null;
                    }
                }
            }
        }
        return "Board does not contain a contradiction";
    }

    @Override
    public String checkContradictionAt(Board board, int elementIndex)
    {
        FillapixBoard fillapixBoard = (FillapixBoard) board;
        int width = fillapixBoard.getWidth();
        FillapixCell cell = fillapixBoard.getCell(elementIndex%width,elementIndex/width);
        if (cell.getValueInt() != -1) {
            int numBlackCells = ((FillapixBoard) board).getNumCells(cell, CellState.BLACK);
            if (numBlackCells > cell.getValueInt()) {
                return null;
            }
        }
        return "Board does not contain a contradiction";
    }

    @Override
    public String checkRule(Board initialBoard, Board finalBoard)
    {
        return checkContradiction(finalBoard);
    }

    @Override
    public String checkRuleAt(Board initialBoard, Board finalBoard, int elementIndex)
    {
        return checkContradictionAt(finalBoard, elementIndex);
    }

    @Override
    public boolean doDefaultApplication(Board initialBoard, Board finalBoard)
    {
        return false;
    }

    @Override
    public boolean doDefaultApplicationAt(Board initialBoard, Board finalBoard, int elementIndex)
    {
        return false;
    }
}
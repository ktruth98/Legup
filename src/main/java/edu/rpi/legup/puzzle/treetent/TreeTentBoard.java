package edu.rpi.legup.puzzle.treetent;

import edu.rpi.legup.model.gameboard.PuzzleElement;
import edu.rpi.legup.model.gameboard.GridBoard;

import java.util.ArrayList;

public class TreeTentBoard extends GridBoard
{

    private ArrayList<TreeTentLine> lines;

    private ArrayList<TreeTentClue> east;
    private ArrayList<TreeTentClue> south;

    public TreeTentBoard(int width, int height)
    {
        super(width, height);

        this.lines = new ArrayList<>();

        this.east = new ArrayList<>();
        this.south = new ArrayList<>();

        for(int i = 0; i < height; i++)
        {
            east.add(null);
        }
        for(int i = 0; i < width; i++)
        {
            south.add(null);
        }
    }

    public TreeTentBoard(int size)
    {
        this(size, size);
    }

    public ArrayList<TreeTentLine> getLines()
    {
        return lines;
    }

    public ArrayList<TreeTentClue> getEast()
    {
        return east;
    }

    public ArrayList<TreeTentClue> getSouth()
    {
        return south;
    }

    @Override
    public TreeTentCell getCell(int x, int y)
    {
        return (TreeTentCell) super.getCell(x, y);
    }

    @Override
    public void notifyChange(PuzzleElement puzzleElement)
    {
        if(puzzleElement instanceof TreeTentLine){
            lines.add((TreeTentLine) puzzleElement);
        }
        else
        {
            super.notifyChange(puzzleElement);
        }
    }

    @Override
    public TreeTentBoard copy()
    {
        TreeTentBoard copy = new TreeTentBoard(dimension.width, dimension.height);
        for(int x = 0; x < this.dimension.width; x++)
        {
            for(int y = 0; y < this.dimension.height; y++)
            {
                copy.setCell(x, y, getCell(x, y).copy());
            }
        }
        for(TreeTentLine line : lines)
        {
            TreeTentLine lineCpy = line.copy();
            lineCpy.setModifiable(false);
            copy.getLines().add(lineCpy);
        }
        return copy;
    }
}

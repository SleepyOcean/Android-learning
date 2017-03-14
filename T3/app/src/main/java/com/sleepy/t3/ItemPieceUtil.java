/*
 * Created by sleepy on 17-2-4 下午7:43
 * Copyright (c) 2017.
 */

package com.sleepy.t3;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

public class ItemPieceUtil {
    public static List<ItemPiece> splitImage(Bitmap bitmap, int piece)
    {
        List<ItemPiece> itemPieces = new ArrayList<ItemPiece>();

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        int pieceWidth = Math.min(width, height) / piece;

        for (int i = 0; i < piece; i++)
        {
            for (int j = 0; j < piece; j++)
            {

                ItemPiece itemPiece = new ItemPiece();
                itemPiece.setIndex(j + i * piece);

                int x = j * pieceWidth;
                int y = i * pieceWidth;

                itemPiece.setBitmap(bitmap);
                itemPieces.add(itemPiece);
            }
        }

        return itemPieces;
    }


}

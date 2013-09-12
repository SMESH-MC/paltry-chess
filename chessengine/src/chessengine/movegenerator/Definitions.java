package chessengine.movegenerator;
/**
 * Diese Klasse enthaelt Konstanten, die in anderen Klassen benutzt werden koennen
 *  
 * @author SMESH
 *
 */
public interface Definitions {
 
    //Konstante fuer leere Felder
    public static final byte leeresFeld = 0;
    //Konstanten fur die weissen Figuren
    public static final byte pawn_w		= 1;
	public static final byte knight_w	= 2;
    public static final byte queen_w	= 3;
    public static final byte bishop_w	= 5;
    public static final byte rook_w		= 6;
    public static final byte king_w		= 7;
    //Konstanten fur die schwarzen Figuren
    public static final byte pawn_b		= -1;
	public static final byte knight_b	= -2;
    public static final byte queen_b	= -3;
    public static final byte bishop_b	= -5;
    public static final byte rook_b		= -6;
    public static final byte king_b		= -7;
    //Konstanten fuer die Zuege
    public static final byte[] pawn_moves	= {16, 32, 15, 17};
    public static final byte[] bishop_moves	= {-17, -15, 15, 17};
    public static final byte[] rook_moves	= {-16, -1, 1, 16};
    public static final byte[] queen_moves	= {-17, -16, -15, -1, 1, 15, 16, 17} ;
    public static final byte[] king_moves	= {-17, -16, -15, -1, 1, 15, 16, 17};
    public static final byte[] knight_moves	= {-33, -31, -18, -14, 14, 18, 31, 33};
    
}

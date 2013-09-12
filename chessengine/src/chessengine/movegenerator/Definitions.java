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
    
    //0x88-Konstanten fuer alle Felder
    public static final byte a8=112; public static final byte b8=113; public static final byte c8=114; public static final byte d8=115; public static final byte e8=116; public static final byte f8=117; public static final byte g8=118; public static final byte h8=119; 
    public static final byte a7=96;  public static final byte b7=97;  public static final byte c7=98;  public static final byte d7=99;  public static final byte e7=100; public static final byte f7=101; public static final byte g7=102; public static final byte h7=103; 
    public static final byte a6=80;  public static final byte b6=81;  public static final byte c6=82;  public static final byte d6=83;  public static final byte e6=84;  public static final byte f6=85;  public static final byte g6=86;  public static final byte h6=87; 
    public static final byte a5=64;  public static final byte b5=65;  public static final byte c5=66;  public static final byte d5=67;  public static final byte e5=68;  public static final byte f5=69;  public static final byte g5=70;  public static final byte h5=71; 
    public static final byte a4=48;  public static final byte b4=49;  public static final byte c4=50;  public static final byte d4=51;  public static final byte e4=52;  public static final byte f4=53;  public static final byte g4=54;  public static final byte h4=55; 
    public static final byte a3=32;  public static final byte b3=33;  public static final byte c3=34;  public static final byte d3=35;  public static final byte e3=36;  public static final byte f3=37;  public static final byte g3=38;  public static final byte h3=39; 
    public static final byte a2=15;  public static final byte b2=17;  public static final byte c2=18;  public static final byte d2=19;  public static final byte e2=20;  public static final byte f2=21;  public static final byte g2=22;  public static final byte h2=23; 
    public static final byte a1=0;   public static final byte b1=1;   public static final byte c1=2;   public static final byte d1=3;   public static final byte e1=4;   public static final byte f1=5;   public static final byte g1=6;   public static final byte h1=7; 
}

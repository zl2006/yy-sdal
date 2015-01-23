package org.yy.dal.parse;

/**
 *  Token Manager.
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  zhouliang
 * @version  [版本号, 2015年1月22日]
 * @since  [产品/模块版本]
 */
@SuppressWarnings("unused")
public class CCJSqlParserTokenManager implements CCJSqlParserConstants {
    
    /** Debug output. */
    public java.io.PrintStream debugStream = System.out;
    
    /** Set debug output. */
    public void setDebugStream(java.io.PrintStream ds) {
        debugStream = ds;
    }
    
    private final int jjStopStringLiteralDfa_0(int pos, long active0, long active1, long active2) {
        switch (pos) {
            case 0:
                if ((active2 & 0x80000L) != 0L)
                    return 8;
                if ((active2 & 0x1L) != 0L)
                    return 1;
                if ((active2 & 0x10040000L) != 0L)
                    return 5;
                if ((active0 & 0xffffffffffffffe0L) != 0L || (active1 & 0x7fffffffffffL) != 0L) {
                    jjmatchedKind = 116;
                    return 43;
                }
                return -1;
            case 1:
                if ((active0 & 0xbfd9ffffff7bf000L) != 0L || (active1 & 0x7ffffffdfeffL) != 0L) {
                    if (jjmatchedPos != 1) {
                        jjmatchedKind = 116;
                        jjmatchedPos = 1;
                    }
                    return 43;
                }
                if ((active0 & 0x4026000000840fe0L) != 0L || (active1 & 0x20100L) != 0L)
                    return 43;
                return -1;
            case 2:
                if ((active0 & 0xffffebffffd00000L) != 0L || (active1 & 0x7efbdffbffffL) != 0L) {
                    if (jjmatchedPos != 2) {
                        jjmatchedKind = 116;
                        jjmatchedPos = 2;
                    }
                    return 43;
                }
                if ((active0 & 0x1400002ff000L) != 0L || (active1 & 0x10420040000L) != 0L)
                    return 43;
                return -1;
            case 3:
                if ((active0 & 0x17fdfc00000L) != 0L || (active1 & 0x600680001200L) != 0L)
                    return 43;
                if ((active0 & 0xffffea8020100000L) != 0L || (active1 & 0x1ef95fffedffL) != 0L) {
                    if (jjmatchedPos != 3) {
                        jjmatchedKind = 116;
                        jjmatchedPos = 3;
                    }
                    return 43;
                }
                return -1;
            case 4:
                if ((active0 & 0xff00000000100000L) != 0L || (active1 & 0x5ef04d7fe5feL) != 0L) {
                    if (jjmatchedPos != 4) {
                        jjmatchedKind = 116;
                        jjmatchedPos = 4;
                    }
                    return 43;
                }
                if ((active0 & 0xffea8020000000L) != 0L || (active1 & 0x992800801L) != 0L)
                    return 43;
                return -1;
            case 5:
                if ((active0 & 0x100000L) != 0L || (active1 & 0x52f00d7fe1fcL) != 0L) {
                    jjmatchedKind = 116;
                    jjmatchedPos = 5;
                    return 43;
                }
                if ((active0 & 0xff00000000000000L) != 0L || (active1 & 0xc0040000403L) != 0L)
                    return 43;
                return -1;
            case 6:
                if ((active0 & 0x100000L) != 0L || (active1 & 0x40800544c03cL) != 0L)
                    return 43;
                if ((active1 & 0x1270083b21c0L) != 0L) {
                    jjmatchedKind = 116;
                    jjmatchedPos = 6;
                    return 43;
                }
                return -1;
            case 7:
                if ((active1 & 0x27000392100L) != 0L) {
                    jjmatchedKind = 116;
                    jjmatchedPos = 7;
                    return 43;
                }
                if ((active1 & 0x1000080200c0L) != 0L)
                    return 43;
                return -1;
            case 8:
                if ((active1 & 0x190000L) != 0L) {
                    jjmatchedKind = 116;
                    jjmatchedPos = 8;
                    return 43;
                }
                if ((active1 & 0x27000202100L) != 0L)
                    return 43;
                return -1;
            case 9:
                if ((active1 & 0x180000L) != 0L)
                    return 43;
                if ((active1 & 0x10000L) != 0L) {
                    jjmatchedKind = 116;
                    jjmatchedPos = 9;
                    return 43;
                }
                return -1;
            case 10:
                if ((active1 & 0x10000L) != 0L) {
                    jjmatchedKind = 116;
                    jjmatchedPos = 10;
                    return 43;
                }
                return -1;
            default:
                return -1;
        }
    }
    
    private final int jjStartNfa_0(int pos, long active0, long active1, long active2) {
        return jjMoveNfa_0(jjStopStringLiteralDfa_0(pos, active0, active1, active2), pos + 1);
    }
    
    private int jjStopAtPos(int pos, int kind) {
        jjmatchedKind = kind;
        jjmatchedPos = pos;
        return pos + 1;
    }
    
    private int jjMoveStringLiteralDfa0_0() {
        switch (curChar) {
            case 33:
                return jjMoveStringLiteralDfa1_0(0x0L, 0x0L, 0x3100L);
            case 37:
                return jjStopAtPos(0, 148);
            case 38:
                return jjStopAtPos(0, 144);
            case 40:
                jjmatchedKind = 125;
                return jjMoveStringLiteralDfa1_0(0x0L, 0x0L, 0x4L);
            case 41:
                return jjStopAtPos(0, 126);
            case 42:
                return jjStopAtPos(0, 127);
            case 43:
                return jjStopAtPos(0, 145);
            case 44:
                return jjStopAtPos(0, 123);
            case 45:
                jjmatchedKind = 146;
                return jjMoveStringLiteralDfa1_0(0x0L, 0x0L, 0x10000000L);
            case 46:
                return jjStartNfaWithStates_0(0, 128, 1);
            case 47:
                return jjStartNfaWithStates_0(0, 147, 8);
            case 58:
                jjmatchedKind = 155;
                return jjMoveStringLiteralDfa1_0(0x0L, 0x0L, 0x4000000L);
            case 59:
                return jjStopAtPos(0, 122);
            case 60:
                jjmatchedKind = 132;
                return jjMoveStringLiteralDfa1_0(0x0L, 0x0L, 0xc0L);
            case 61:
                return jjStopAtPos(0, 124);
            case 62:
                jjmatchedKind = 131;
                return jjMoveStringLiteralDfa1_0(0x0L, 0x0L, 0x20L);
            case 63:
                return jjStopAtPos(0, 129);
            case 64:
                return jjMoveStringLiteralDfa1_0(0x0L, 0x0L, 0x200L);
            case 94:
                return jjStopAtPos(0, 149);
            case 65:
            case 97:
                return jjMoveStringLiteralDfa1_0(0x47020L, 0x30000000L, 0x0L);
            case 66:
            case 98:
                return jjMoveStringLiteralDfa1_0(0x1000000000040L, 0x40000000020L, 0x0L);
            case 67:
            case 99:
                return jjMoveStringLiteralDfa1_0(0x200000120000000L, 0x8041280200L, 0x0L);
            case 68:
            case 100:
                return jjMoveStringLiteralDfa1_0(0x100000004400080L, 0x80L, 0x0L);
            case 69:
            case 101:
                return jjMoveStringLiteralDfa1_0(0x1000000800200000L, 0x600000004402L, 0x0L);
            case 70:
            case 102:
                return jjMoveStringLiteralDfa1_0(0x42040000000L, 0x4100040000L, 0x0L);
            case 71:
            case 103:
                return jjMoveStringLiteralDfa1_0(0x800000000000L, 0x0L, 0x0L);
            case 72:
            case 104:
                return jjMoveStringLiteralDfa1_0(0x2000000000000000L, 0x0L, 0x0L);
            case 73:
            case 105:
                return jjMoveStringLiteralDfa1_0(0x4006000000800300L, 0x20100L, 0x0L);
            case 74:
            case 106:
                return jjMoveStringLiteralDfa1_0(0x8000000L, 0x0L, 0x0L);
            case 75:
            case 107:
                return jjMoveStringLiteralDfa1_0(0x8000L, 0x0L, 0x0L);
            case 76:
            case 108:
                return jjMoveStringLiteralDfa1_0(0x8000012000000L, 0x200008000L, 0x0L);
            case 77:
            case 109:
                return jjMoveStringLiteralDfa1_0(0x0L, 0x10800L, 0x0L);
            case 78:
            case 110:
                return jjMoveStringLiteralDfa1_0(0x1010000L, 0x84000008L, 0x0L);
            case 79:
            case 111:
                return jjMoveStringLiteralDfa1_0(0x830000080000c00L, 0x1000L, 0x0L);
            case 80:
            case 112:
                return jjMoveStringLiteralDfa1_0(0x80000100000L, 0x2002002004L, 0x0L);
            case 82:
            case 114:
                return jjMoveStringLiteralDfa1_0(0x40000000000000L, 0xb0c00100010L, 0x0L);
            case 83:
            case 115:
                return jjMoveStringLiteralDfa1_0(0x400001000020000L, 0x8800000L, 0x0L);
            case 84:
            case 116:
                return jjMoveStringLiteralDfa1_0(0x8400080000L, 0x40L, 0x0L);
            case 85:
            case 117:
                return jjMoveStringLiteralDfa1_0(0x8000600000000000L, 0x101000000000L, 0x0L);
            case 86:
            case 118:
                return jjMoveStringLiteralDfa1_0(0x80010000000000L, 0x400001L, 0x0L);
            case 87:
            case 119:
                return jjMoveStringLiteralDfa1_0(0x24200000000L, 0x0L, 0x0L);
            case 88:
            case 120:
                return jjMoveStringLiteralDfa1_0(0x100000000000L, 0x0L, 0x0L);
            case 123:
                return jjMoveStringLiteralDfa1_0(0x0L, 0x0L, 0x23400000L);
            case 124:
                jjmatchedKind = 143;
                return jjMoveStringLiteralDfa1_0(0x0L, 0x0L, 0x4000L);
            case 125:
                return jjStopAtPos(0, 151);
            case 126:
                jjmatchedKind = 138;
                return jjMoveStringLiteralDfa1_0(0x0L, 0x0L, 0x800L);
            default:
                return jjMoveNfa_0(7, 0);
        }
    }
    
    private int jjMoveStringLiteralDfa1_0(long active0, long active1, long active2) {
        try {
            curChar = input_stream.readChar();
        }
        catch (java.io.IOException e) {
            jjStopStringLiteralDfa_0(0, active0, active1, active2);
            return 1;
        }
        switch (curChar) {
            case 42:
                if ((active2 & 0x800L) != 0L)
                    return jjStopAtPos(1, 139);
                break;
            case 43:
                return jjMoveStringLiteralDfa2_0(active0, 0L, active1, 0L, active2, 0x4L);
            case 58:
                if ((active2 & 0x4000000L) != 0L)
                    return jjStopAtPos(1, 154);
                break;
            case 61:
                if ((active2 & 0x20L) != 0L)
                    return jjStopAtPos(1, 133);
                else if ((active2 & 0x40L) != 0L)
                    return jjStopAtPos(1, 134);
                else if ((active2 & 0x100L) != 0L)
                    return jjStopAtPos(1, 136);
                break;
            case 62:
                if ((active2 & 0x80L) != 0L)
                    return jjStopAtPos(1, 135);
                else if ((active2 & 0x10000000L) != 0L)
                    return jjStopAtPos(1, 156);
                break;
            case 64:
                if ((active2 & 0x200L) != 0L)
                    return jjStopAtPos(1, 137);
                break;
            case 65:
            case 97:
                return jjMoveStringLiteralDfa2_0(active0, 0x2080008100000000L, active1, 0xa0041a209L, active2, 0L);
            case 68:
            case 100:
                if ((active2 & 0x400000L) != 0L)
                    return jjStopAtPos(1, 150);
                return jjMoveStringLiteralDfa2_0(active0, 0L, active1, 0x20000000L, active2, 0L);
            case 69:
            case 101:
                return jjMoveStringLiteralDfa2_0(active0, 0x501000010528000L, active1, 0xa0000100030L, active2, 0L);
            case 70:
            case 102:
                return jjMoveStringLiteralDfa2_0(active0, 0x800000000000000L, active1, 0L, active2, 0x20000000L);
            case 72:
            case 104:
                return jjMoveStringLiteralDfa2_0(active0, 0x20600000000L, active1, 0x200000L, active2, 0L);
            case 73:
            case 105:
                return jjMoveStringLiteralDfa2_0(active0, 0x48094002000000L, active1, 0x40108000880L, active2, 0L);
            case 76:
            case 108:
                return jjMoveStringLiteralDfa2_0(active0, 0x800001000L, active1, 0x10000000L, active2, 0L);
            case 77:
            case 109:
                return jjMoveStringLiteralDfa2_0(active0, 0x100000000000L, active1, 0L, active2, 0L);
            case 78:
            case 110:
                if ((active0 & 0x200L) != 0L) {
                    jjmatchedKind = 9;
                    jjmatchedPos = 1;
                }
                else if ((active0 & 0x800L) != 0L)
                    return jjStartNfaWithStates_0(1, 11, 43);
                return jjMoveStringLiteralDfa2_0(active0, 0x4006400000a06000L, active1, 0x101000020100L, active2, 0L);
            case 79:
            case 111:
                if ((active0 & 0x80L) != 0L)
                    return jjStartNfaWithStates_0(1, 7, 43);
                return jjMoveStringLiteralDfa2_0(active0, 0x41008090000L, active1, 0x144450c0000L, active2, 0L);
            case 80:
            case 112:
                return jjMoveStringLiteralDfa2_0(active0, 0x8000000080000000L, active1, 0L, active2, 0L);
            case 82:
            case 114:
                if ((active0 & 0x400L) != 0L) {
                    jjmatchedKind = 10;
                    jjmatchedPos = 1;
                }
                return jjMoveStringLiteralDfa2_0(active0, 0x220800064000000L, active1, 0x2002000044L, active2, 0L);
            case 83:
            case 115:
                if ((active0 & 0x20L) != 0L) {
                    jjmatchedKind = 5;
                    jjmatchedPos = 1;
                }
                else if ((active0 & 0x100L) != 0L)
                    return jjStartNfaWithStates_0(1, 8, 43);
                return jjMoveStringLiteralDfa2_0(active0, 0x200000040000L, active1, 0x2L, active2, 0L);
            case 84:
            case 116:
                if ((active2 & 0x1000000L) != 0L) {
                    jjmatchedKind = 152;
                    jjmatchedPos = 1;
                }
                return jjMoveStringLiteralDfa2_0(active0, 0L, active1, 0x800000L, active2, 0x2000000L);
            case 85:
            case 117:
                return jjMoveStringLiteralDfa2_0(active0, 0x10002001000000L, active1, 0x8080000000L, active2, 0L);
            case 86:
            case 118:
                return jjMoveStringLiteralDfa2_0(active0, 0L, active1, 0x1000L, active2, 0L);
            case 88:
            case 120:
                return jjMoveStringLiteralDfa2_0(active0, 0x1000000000000000L, active1, 0x600000004400L, active2, 0L);
            case 89:
            case 121:
                if ((active0 & 0x40L) != 0L)
                    return jjStartNfaWithStates_0(1, 6, 43);
                break;
            case 124:
                if ((active2 & 0x4000L) != 0L)
                    return jjStopAtPos(1, 142);
                break;
            case 126:
                if ((active2 & 0x1000L) != 0L) {
                    jjmatchedKind = 140;
                    jjmatchedPos = 1;
                }
                return jjMoveStringLiteralDfa2_0(active0, 0L, active1, 0L, active2, 0x2000L);
            default:
                break;
        }
        return jjStartNfa_0(0, active0, active1, active2);
    }
    
    private int jjMoveStringLiteralDfa2_0(long old0, long active0, long old1, long active1, long old2, long active2) {
        if (((active0 &= old0) | (active1 &= old1) | (active2 &= old2)) == 0L)
            return jjStartNfa_0(0, old0, old1, old2);
        try {
            curChar = input_stream.readChar();
        }
        catch (java.io.IOException e) {
            jjStopStringLiteralDfa_0(1, active0, active1, active2);
            return 2;
        }
        switch (curChar) {
            case 41:
                if ((active2 & 0x4L) != 0L)
                    return jjStopAtPos(2, 130);
                break;
            case 42:
                if ((active2 & 0x2000L) != 0L)
                    return jjStopAtPos(2, 141);
                break;
            case 65:
            case 97:
                return jjMoveStringLiteralDfa3_0(active0, 0L, active1, 0xa00000L, active2, 0L);
            case 66:
            case 98:
                return jjMoveStringLiteralDfa3_0(active0, 0x8000000000L, active1, 0x1008000000L, active2, 0L);
            case 67:
            case 99:
                if ((active0 & 0x40000L) != 0L)
                    return jjStartNfaWithStates_0(2, 18, 43);
                return jjMoveStringLiteralDfa3_0(active0, 0L, active1, 0x4000402L, active2, 0L);
            case 68:
            case 100:
                if ((active0 & 0x2000L) != 0L)
                    return jjStartNfaWithStates_0(2, 13, 43);
                else if ((active0 & 0x200000L) != 0L)
                    return jjStartNfaWithStates_0(2, 21, 43);
                else if ((active1 & 0x20000000L) != 0L)
                    return jjStartNfaWithStates_0(2, 93, 43);
                return jjMoveStringLiteralDfa3_0(active0, 0x8022000000000000L, active1, 0L, active2, 0L);
            case 69:
            case 101:
                return jjMoveStringLiteralDfa3_0(active0, 0x200030680000000L, active1, 0x602000001000L, active2, 0L);
            case 70:
            case 102:
                return jjMoveStringLiteralDfa3_0(active0, 0x800000010000000L, active1, 0x100000L, active2, 0L);
            case 71:
            case 103:
                return jjMoveStringLiteralDfa3_0(active0, 0x41000000000000L, active1, 0x80000000000L, active2, 0L);
            case 73:
            case 105:
                return jjMoveStringLiteralDfa3_0(active0, 0x1000600008000000L, active1, 0x2000004L, active2, 0L);
            case 75:
            case 107:
                return jjMoveStringLiteralDfa3_0(active0, 0x2000000L, active1, 0L, active2, 0L);
            case 76:
            case 108:
                if ((active0 & 0x1000L) != 0L)
                    return jjStartNfaWithStates_0(2, 12, 43);
                else if ((active0 & 0x100000000000L) != 0L)
                    return jjStartNfaWithStates_0(2, 44, 43);
                return jjMoveStringLiteralDfa3_0(active0, 0x580002001000000L, active1, 0x1040c0000001L, active2, 0L);
            case 77:
            case 109:
                return jjMoveStringLiteralDfa3_0(active0, 0x8001000000000L, active1, 0L, active2, 0L);
            case 78:
            case 110:
                if ((active2 & 0x20000000L) != 0L)
                    return jjStopAtPos(2, 157);
                return jjMoveStringLiteralDfa3_0(active0, 0x4000000000000L, active1, 0x40801080800L, active2, 0L);
            case 79:
            case 111:
                return jjMoveStringLiteralDfa3_0(active0, 0x800064000000L, active1, 0L, active2, 0L);
            case 80:
            case 112:
                if ((active0 & 0x80000L) != 0L)
                    return jjStartNfaWithStates_0(2, 19, 43);
                return jjMoveStringLiteralDfa3_0(active0, 0L, active1, 0x10L, active2, 0L);
            case 82:
            case 114:
                if ((active0 & 0x40000000000L) != 0L) {
                    jjmatchedKind = 42;
                    jjmatchedPos = 2;
                }
                return jjMoveStringLiteralDfa3_0(active0, 0x100000L, active1, 0x8100442000L, active2, 0L);
            case 83:
            case 115:
                if ((active2 & 0x2000000L) != 0L)
                    return jjStopAtPos(2, 153);
                return jjMoveStringLiteralDfa3_0(active0, 0x4000000900400000L, active1, 0x200000280L, active2, 0L);
            case 84:
            case 116:
                if ((active0 & 0x10000L) != 0L)
                    return jjStartNfaWithStates_0(2, 16, 43);
                else if ((active0 & 0x20000L) != 0L)
                    return jjStartNfaWithStates_0(2, 17, 43);
                return jjMoveStringLiteralDfa3_0(active0, 0x10004000800000L, active1, 0x2001003c128L, active2, 0L);
            case 85:
            case 117:
                return jjMoveStringLiteralDfa3_0(active0, 0L, active1, 0x40L, active2, 0L);
            case 86:
            case 118:
                return jjMoveStringLiteralDfa3_0(active0, 0x2000080000000000L, active1, 0L, active2, 0L);
            case 87:
            case 119:
                if ((active1 & 0x10000000000L) != 0L) {
                    jjmatchedKind = 104;
                    jjmatchedPos = 2;
                }
                return jjMoveStringLiteralDfa3_0(active0, 0L, active1, 0x400000000L, active2, 0L);
            case 89:
            case 121:
                if ((active0 & 0x4000L) != 0L)
                    return jjStartNfaWithStates_0(2, 14, 43);
                else if ((active0 & 0x8000L) != 0L)
                    return jjStartNfaWithStates_0(2, 15, 43);
                break;
            default:
                break;
        }
        return jjStartNfa_0(1, active0, active1, active2);
    }
    
    private int jjMoveStringLiteralDfa3_0(long old0, long active0, long old1, long active1, long old2, long active2) {
        if (((active0 &= old0) | (active1 &= old1) | (active2 &= old2)) == 0L)
            return jjStartNfa_0(1, old0, old1, old2);
        try {
            curChar = input_stream.readChar();
        }
        catch (java.io.IOException e) {
            jjStopStringLiteralDfa_0(2, active0, active1, 0L);
            return 3;
        }
        switch (curChar) {
            case 65:
            case 97:
                return jjMoveStringLiteralDfa4_0(active0, 0x8200000000000000L, active1, 0x40000000002L);
            case 67:
            case 99:
                if ((active0 & 0x400000L) != 0L)
                    return jjStartNfaWithStates_0(3, 22, 43);
                else if ((active1 & 0x200000000000L) != 0L) {
                    jjmatchedKind = 109;
                    jjmatchedPos = 3;
                }
                return jjMoveStringLiteralDfa4_0(active0, 0x100000L, active1, 0x402000000000L);
            case 69:
            case 101:
                if ((active0 & 0x2000000L) != 0L)
                    return jjStartNfaWithStates_0(3, 25, 43);
                else if ((active0 & 0x100000000L) != 0L)
                    return jjStartNfaWithStates_0(3, 32, 43);
                else if ((active0 & 0x800000000L) != 0L)
                    return jjStartNfaWithStates_0(3, 35, 43);
                else if ((active0 & 0x1000000000L) != 0L)
                    return jjStartNfaWithStates_0(3, 36, 43);
                return jjMoveStringLiteralDfa4_0(active0, 0x4536000000000000L, active1, 0x80010178500L);
            case 71:
            case 103:
                return jjMoveStringLiteralDfa4_0(active0, 0L, active1, 0x800000000L);
            case 72:
            case 104:
                if ((active0 & 0x4000000000L) != 0L)
                    return jjStartNfaWithStates_0(3, 38, 43);
                return jjMoveStringLiteralDfa4_0(active0, 0x40000000000000L, active1, 0L);
            case 73:
            case 105:
                return jjMoveStringLiteralDfa4_0(active0, 0x2009000000000000L, active1, 0L);
            case 76:
            case 108:
                if ((active0 & 0x1000000L) != 0L) {
                    jjmatchedKind = 24;
                    jjmatchedPos = 3;
                }
                else if ((active0 & 0x2000000000L) != 0L)
                    return jjStartNfaWithStates_0(3, 37, 43);
                return jjMoveStringLiteralDfa4_0(active0, 0x8000000000L, active1, 0x4088000010L);
            case 77:
            case 109:
                if ((active0 & 0x40000000L) != 0L)
                    return jjStartNfaWithStates_0(3, 30, 43);
                return jjMoveStringLiteralDfa4_0(active0, 0L, active1, 0x4L);
            case 78:
            case 110:
                if ((active0 & 0x8000000L) != 0L)
                    return jjStartNfaWithStates_0(3, 27, 43);
                else if ((active0 & 0x80000000L) != 0L)
                    return jjStartNfaWithStates_0(3, 31, 43);
                else if ((active0 & 0x200000000L) != 0L)
                    return jjStartNfaWithStates_0(3, 33, 43);
                else if ((active0 & 0x400000000L) != 0L)
                    return jjStartNfaWithStates_0(3, 34, 43);
                return jjMoveStringLiteralDfa4_0(active0, 0x200000000000L, active1, 0x1000040L);
            case 79:
            case 111:
                if ((active0 & 0x800000L) != 0L)
                    return jjStartNfaWithStates_0(3, 23, 43);
                return jjMoveStringLiteralDfa4_0(active0, 0x480000000000L, active1, 0x101002000000L);
            case 80:
            case 112:
                if ((active0 & 0x4000000L) != 0L)
                    return jjStartNfaWithStates_0(3, 26, 43);
                break;
            case 82:
            case 114:
                if ((active1 & 0x1000L) != 0L)
                    return jjStartNfaWithStates_0(3, 76, 43);
                return jjMoveStringLiteralDfa4_0(active0, 0x20000000000L, active1, 0x8000a04000L);
            case 83:
            case 115:
                if ((active1 & 0x400000000L) != 0L)
                    return jjStartNfaWithStates_0(3, 98, 43);
                return jjMoveStringLiteralDfa4_0(active0, 0x1800000020000000L, active1, 0x100080000L);
            case 84:
            case 116:
                if ((active0 & 0x10000000L) != 0L)
                    return jjStartNfaWithStates_0(3, 28, 43);
                else if ((active1 & 0x200L) != 0L)
                    return jjStartNfaWithStates_0(3, 73, 43);
                else if ((active1 & 0x200000000L) != 0L)
                    return jjStartNfaWithStates_0(3, 97, 43);
                return jjMoveStringLiteralDfa4_0(active0, 0L, active1, 0x2080L);
            case 85:
            case 117:
                return jjMoveStringLiteralDfa4_0(active0, 0x80800000000000L, active1, 0x20040000809L);
            case 87:
            case 119:
                if ((active0 & 0x10000000000L) != 0L)
                    return jjStartNfaWithStates_0(3, 40, 43);
                return jjMoveStringLiteralDfa4_0(active0, 0L, active1, 0x20L);
            case 89:
            case 121:
                return jjMoveStringLiteralDfa4_0(active0, 0L, active1, 0x4400000L);
            default:
                break;
        }
        return jjStartNfa_0(2, active0, active1, 0L);
    }
    
    private int jjMoveStringLiteralDfa4_0(long old0, long active0, long old1, long active1) {
        if (((active0 &= old0) | (active1 &= old1)) == 0L)
            return jjStartNfa_0(2, old0, old1, 0L);
        try {
            curChar = input_stream.readChar();
        }
        catch (java.io.IOException e) {
            jjStopStringLiteralDfa_0(3, active0, active1, 0L);
            return 4;
        }
        switch (curChar) {
            case 65:
            case 97:
                return jjMoveStringLiteralDfa5_0(active0, 0L, active1, 0x204014L);
            case 67:
            case 99:
                return jjMoveStringLiteralDfa5_0(active0, 0x400000000000000L, active1, 0x4000040L);
            case 69:
            case 101:
                if ((active0 & 0x8000000000L) != 0L)
                    return jjStartNfaWithStates_0(4, 39, 43);
                else if ((active0 & 0x20000000000L) != 0L)
                    return jjStartNfaWithStates_0(4, 41, 43);
                else if ((active0 & 0x80000000000000L) != 0L) {
                    jjmatchedKind = 55;
                    jjmatchedPos = 4;
                }
                else if ((active1 & 0x800000000L) != 0L)
                    return jjStartNfaWithStates_0(4, 99, 43);
                return jjMoveStringLiteralDfa5_0(active0, 0x800000000100000L, active1, 0xa001000021L);
            case 71:
            case 103:
                if ((active0 & 0x200000000000L) != 0L)
                    return jjStartNfaWithStates_0(4, 45, 43);
                return jjMoveStringLiteralDfa5_0(active0, 0L, active1, 0x100000000000L);
            case 73:
            case 105:
                return jjMoveStringLiteralDfa5_0(active0, 0L, active1, 0x8442080L);
            case 77:
            case 109:
                return jjMoveStringLiteralDfa5_0(active0, 0L, active1, 0x40000000L);
            case 78:
            case 110:
                if ((active0 & 0x400000000000L) != 0L)
                    return jjStartNfaWithStates_0(4, 46, 43);
                else if ((active0 & 0x1000000000000L) != 0L)
                    return jjStartNfaWithStates_0(4, 48, 43);
                return jjMoveStringLiteralDfa5_0(active0, 0x2000000000000000L, active1, 0L);
            case 79:
            case 111:
                return jjMoveStringLiteralDfa5_0(active0, 0L, active1, 0x4000000000L);
            case 80:
            case 112:
                if ((active0 & 0x800000000000L) != 0L)
                    return jjStartNfaWithStates_0(4, 47, 43);
                return jjMoveStringLiteralDfa5_0(active0, 0L, active1, 0x402L);
            case 82:
            case 114:
                if ((active0 & 0x4000000000000L) != 0L)
                    return jjStartNfaWithStates_0(4, 50, 43);
                else if ((active0 & 0x10000000000000L) != 0L)
                    return jjStartNfaWithStates_0(4, 52, 43);
                else if ((active0 & 0x20000000000000L) != 0L)
                    return jjStartNfaWithStates_0(4, 53, 43);
                else if ((active1 & 0x2000000L) != 0L)
                    return jjStartNfaWithStates_0(4, 89, 43);
                else if ((active1 & 0x10000000L) != 0L)
                    return jjStartNfaWithStates_0(4, 92, 43);
                return jjMoveStringLiteralDfa5_0(active0, 0x4000000000000000L, active1, 0x60000138108L);
            case 83:
            case 115:
                if ((active0 & 0x20000000L) != 0L)
                    return jjStartNfaWithStates_0(4, 29, 43);
                else if ((active1 & 0x800L) != 0L)
                    return jjStartNfaWithStates_0(4, 75, 43);
                else if ((active1 & 0x80000000L) != 0L)
                    return jjStartNfaWithStates_0(4, 95, 43);
                break;
            case 84:
            case 116:
                if ((active0 & 0x80000000000L) != 0L)
                    return jjStartNfaWithStates_0(4, 43, 43);
                else if ((active0 & 0x8000000000000L) != 0L)
                    return jjStartNfaWithStates_0(4, 51, 43);
                else if ((active0 & 0x40000000000000L) != 0L)
                    return jjStartNfaWithStates_0(4, 54, 43);
                else if ((active1 & 0x800000L) != 0L)
                    return jjStartNfaWithStates_0(4, 87, 43);
                else if ((active1 & 0x100000000L) != 0L)
                    return jjStartNfaWithStates_0(4, 96, 43);
                return jjMoveStringLiteralDfa5_0(active0, 0x9300000000000000L, active1, 0x80000L);
            case 85:
            case 117:
                return jjMoveStringLiteralDfa5_0(active0, 0L, active1, 0x401000000000L);
            case 88:
            case 120:
                if ((active0 & 0x2000000000000L) != 0L)
                    return jjStartNfaWithStates_0(4, 49, 43);
                return jjMoveStringLiteralDfa5_0(active0, 0L, active1, 0x80000000000L);
            default:
                break;
        }
        return jjStartNfa_0(3, active0, active1, 0L);
    }
    
    private int jjMoveStringLiteralDfa5_0(long old0, long active0, long old1, long active1) {
        if (((active0 &= old0) | (active1 &= old1)) == 0L)
            return jjStartNfa_0(3, old0, old1, 0L);
        try {
            curChar = input_stream.readChar();
        }
        catch (java.io.IOException e) {
            jjStopStringLiteralDfa_0(4, active0, active1, 0L);
            return 5;
        }
        switch (curChar) {
            case 65:
            case 97:
                return jjMoveStringLiteralDfa6_0(active0, 0L, active1, 0x8048L);
            case 67:
            case 99:
                return jjMoveStringLiteralDfa6_0(active0, 0L, active1, 0x1204010L);
            case 68:
            case 100:
                return jjMoveStringLiteralDfa6_0(active0, 0L, active1, 0x2000000000L);
            case 69:
            case 101:
                if ((active0 & 0x100000000000000L) != 0L)
                    return jjStartNfaWithStates_0(5, 56, 43);
                else if ((active0 & 0x200000000000000L) != 0L)
                    return jjStartNfaWithStates_0(5, 57, 43);
                else if ((active0 & 0x8000000000000000L) != 0L)
                    return jjStartNfaWithStates_0(5, 63, 43);
                else if ((active1 & 0x2L) != 0L)
                    return jjStartNfaWithStates_0(5, 65, 43);
                return jjMoveStringLiteralDfa6_0(active0, 0L, active1, 0x100020L);
            case 71:
            case 103:
                if ((active0 & 0x2000000000000000L) != 0L)
                    return jjStartNfaWithStates_0(5, 61, 43);
                return jjMoveStringLiteralDfa6_0(active0, 0L, active1, 0x100000040000L);
            case 73:
            case 105:
                return jjMoveStringLiteralDfa6_0(active0, 0L, active1, 0x10000L);
            case 76:
            case 108:
                return jjMoveStringLiteralDfa6_0(active0, 0L, active1, 0x4000000L);
            case 78:
            case 110:
                if ((active1 & 0x40000000L) != 0L)
                    return jjStartNfaWithStates_0(5, 94, 43);
                return jjMoveStringLiteralDfa6_0(active0, 0x100000L, active1, 0x29008400080L);
            case 80:
            case 112:
                if ((active1 & 0x80000000000L) != 0L)
                    return jjStartNfaWithStates_0(5, 107, 43);
                break;
            case 82:
            case 114:
                return jjMoveStringLiteralDfa6_0(active0, 0L, active1, 0x80004L);
            case 83:
            case 115:
                if ((active0 & 0x1000000000000000L) != 0L)
                    return jjStartNfaWithStates_0(5, 60, 43);
                else if ((active1 & 0x1L) != 0L)
                    return jjStartNfaWithStates_0(5, 64, 43);
                return jjMoveStringLiteralDfa6_0(active0, 0L, active1, 0x100L);
            case 84:
            case 116:
                if ((active0 & 0x400000000000000L) != 0L)
                    return jjStartNfaWithStates_0(5, 58, 43);
                else if ((active0 & 0x800000000000000L) != 0L)
                    return jjStartNfaWithStates_0(5, 59, 43);
                else if ((active0 & 0x4000000000000000L) != 0L)
                    return jjStartNfaWithStates_0(5, 62, 43);
                else if ((active1 & 0x400L) != 0L)
                    return jjStartNfaWithStates_0(5, 74, 43);
                return jjMoveStringLiteralDfa6_0(active0, 0L, active1, 0x400000002000L);
            case 86:
            case 118:
                return jjMoveStringLiteralDfa6_0(active0, 0L, active1, 0x20000L);
            case 87:
            case 119:
                return jjMoveStringLiteralDfa6_0(active0, 0L, active1, 0x4000000000L);
            case 89:
            case 121:
                if ((active1 & 0x40000000000L) != 0L)
                    return jjStartNfaWithStates_0(5, 106, 43);
                break;
            default:
                break;
        }
        return jjStartNfa_0(4, active0, active1, 0L);
    }
    
    private int jjMoveStringLiteralDfa6_0(long old0, long active0, long old1, long active1) {
        if (((active0 &= old0) | (active1 &= old1)) == 0L)
            return jjStartNfa_0(4, old0, old1, 0L);
        try {
            curChar = input_stream.readChar();
        }
        catch (java.io.IOException e) {
            jjStopStringLiteralDfa_0(5, active0, active1, 0L);
            return 6;
        }
        switch (curChar) {
            case 65:
            case 97:
                return jjMoveStringLiteralDfa7_0(active0, 0L, active1, 0xb0000L);
            case 67:
            case 99:
                return jjMoveStringLiteralDfa7_0(active0, 0L, active1, 0x80L);
            case 68:
            case 100:
                return jjMoveStringLiteralDfa7_0(active0, 0L, active1, 0x1000000000L);
            case 69:
            case 101:
                if ((active1 & 0x10L) != 0L)
                    return jjStartNfaWithStates_0(6, 68, 43);
                else if ((active1 & 0x4000000L) != 0L)
                    return jjStartNfaWithStates_0(6, 90, 43);
                else if ((active1 & 0x400000000000L) != 0L)
                    return jjStartNfaWithStates_0(6, 110, 43);
                return jjMoveStringLiteralDfa7_0(active0, 0L, active1, 0x100000000100L);
            case 71:
            case 103:
                if ((active1 & 0x400000L) != 0L)
                    return jjStartNfaWithStates_0(6, 86, 43);
                return jjMoveStringLiteralDfa7_0(active0, 0L, active1, 0x8000000L);
            case 73:
            case 105:
                return jjMoveStringLiteralDfa7_0(active0, 0L, active1, 0x26000002000L);
            case 76:
            case 108:
                if ((active1 & 0x8L) != 0L)
                    return jjStartNfaWithStates_0(6, 67, 43);
                else if ((active1 & 0x8000L) != 0L)
                    return jjStartNfaWithStates_0(6, 79, 43);
                break;
            case 78:
            case 110:
                if ((active1 & 0x20L) != 0L)
                    return jjStartNfaWithStates_0(6, 69, 43);
                else if ((active1 & 0x40000L) != 0L)
                    return jjStartNfaWithStates_0(6, 82, 43);
                return jjMoveStringLiteralDfa7_0(active0, 0L, active1, 0x100000L);
            case 84:
            case 116:
                if ((active0 & 0x100000L) != 0L)
                    return jjStartNfaWithStates_0(6, 20, 43);
                else if ((active1 & 0x4000L) != 0L)
                    return jjStartNfaWithStates_0(6, 78, 43);
                else if ((active1 & 0x1000000L) != 0L)
                    return jjStartNfaWithStates_0(6, 88, 43);
                else if ((active1 & 0x8000000000L) != 0L)
                    return jjStartNfaWithStates_0(6, 103, 43);
                return jjMoveStringLiteralDfa7_0(active0, 0L, active1, 0x200040L);
            case 89:
            case 121:
                if ((active1 & 0x4L) != 0L)
                    return jjStartNfaWithStates_0(6, 66, 43);
                break;
            default:
                break;
        }
        return jjStartNfa_0(5, active0, active1, 0L);
    }
    
    private int jjMoveStringLiteralDfa7_0(long old0, long active0, long old1, long active1) {
        if (((active0 &= old0) | (active1 &= old1)) == 0L)
            return jjStartNfa_0(5, old0, old1, 0L);
        try {
            curChar = input_stream.readChar();
        }
        catch (java.io.IOException e) {
            jjStopStringLiteralDfa_0(6, 0L, active1, 0L);
            return 7;
        }
        switch (curChar) {
            case 67:
            case 99:
                return jjMoveStringLiteralDfa8_0(active1, 0x100100L);
            case 68:
            case 100:
                if ((active1 & 0x100000000000L) != 0L)
                    return jjStartNfaWithStates_0(7, 108, 43);
                break;
            case 69:
            case 101:
                if ((active1 & 0x40L) != 0L)
                    return jjStartNfaWithStates_0(7, 70, 43);
                return jjMoveStringLiteralDfa8_0(active1, 0x1000200000L);
            case 73:
            case 105:
                return jjMoveStringLiteralDfa8_0(active1, 0x80000L);
            case 76:
            case 108:
                if ((active1 & 0x20000L) != 0L)
                    return jjStartNfaWithStates_0(7, 81, 43);
                return jjMoveStringLiteralDfa8_0(active1, 0x10000L);
            case 78:
            case 110:
                return jjMoveStringLiteralDfa8_0(active1, 0x26000000000L);
            case 79:
            case 111:
                return jjMoveStringLiteralDfa8_0(active1, 0x2000L);
            case 83:
            case 115:
                if ((active1 & 0x8000000L) != 0L)
                    return jjStartNfaWithStates_0(7, 91, 43);
                break;
            case 84:
            case 116:
                if ((active1 & 0x80L) != 0L)
                    return jjStartNfaWithStates_0(7, 71, 43);
                break;
            default:
                break;
        }
        return jjStartNfa_0(6, 0L, active1, 0L);
    }
    
    private int jjMoveStringLiteralDfa8_0(long old1, long active1) {
        if (((active1 &= old1)) == 0L)
            return jjStartNfa_0(6, 0L, old1, 0L);
        try {
            curChar = input_stream.readChar();
        }
        catch (java.io.IOException e) {
            jjStopStringLiteralDfa_0(7, 0L, active1, 0L);
            return 8;
        }
        switch (curChar) {
            case 68:
            case 100:
                if ((active1 & 0x1000000000L) != 0L)
                    return jjStartNfaWithStates_0(8, 100, 43);
                break;
            case 69:
            case 101:
                return jjMoveStringLiteralDfa9_0(active1, 0x100000L);
            case 71:
            case 103:
                if ((active1 & 0x2000000000L) != 0L)
                    return jjStartNfaWithStates_0(8, 101, 43);
                else if ((active1 & 0x4000000000L) != 0L)
                    return jjStartNfaWithStates_0(8, 102, 43);
                else if ((active1 & 0x20000000000L) != 0L)
                    return jjStartNfaWithStates_0(8, 105, 43);
                break;
            case 73:
            case 105:
                return jjMoveStringLiteralDfa9_0(active1, 0x10000L);
            case 78:
            case 110:
                if ((active1 & 0x2000L) != 0L)
                    return jjStartNfaWithStates_0(8, 77, 43);
                return jjMoveStringLiteralDfa9_0(active1, 0x80000L);
            case 82:
            case 114:
                if ((active1 & 0x200000L) != 0L)
                    return jjStartNfaWithStates_0(8, 85, 43);
                break;
            case 84:
            case 116:
                if ((active1 & 0x100L) != 0L)
                    return jjStartNfaWithStates_0(8, 72, 43);
                break;
            default:
                break;
        }
        return jjStartNfa_0(7, 0L, active1, 0L);
    }
    
    private int jjMoveStringLiteralDfa9_0(long old1, long active1) {
        if (((active1 &= old1)) == 0L)
            return jjStartNfa_0(7, 0L, old1, 0L);
        try {
            curChar = input_stream.readChar();
        }
        catch (java.io.IOException e) {
            jjStopStringLiteralDfa_0(8, 0L, active1, 0L);
            return 9;
        }
        switch (curChar) {
            case 83:
            case 115:
                if ((active1 & 0x100000L) != 0L)
                    return jjStartNfaWithStates_0(9, 84, 43);
                break;
            case 84:
            case 116:
                if ((active1 & 0x80000L) != 0L)
                    return jjStartNfaWithStates_0(9, 83, 43);
                break;
            case 90:
            case 122:
                return jjMoveStringLiteralDfa10_0(active1, 0x10000L);
            default:
                break;
        }
        return jjStartNfa_0(8, 0L, active1, 0L);
    }
    
    private int jjMoveStringLiteralDfa10_0(long old1, long active1) {
        if (((active1 &= old1)) == 0L)
            return jjStartNfa_0(8, 0L, old1, 0L);
        try {
            curChar = input_stream.readChar();
        }
        catch (java.io.IOException e) {
            jjStopStringLiteralDfa_0(9, 0L, active1, 0L);
            return 10;
        }
        switch (curChar) {
            case 69:
            case 101:
                return jjMoveStringLiteralDfa11_0(active1, 0x10000L);
            default:
                break;
        }
        return jjStartNfa_0(9, 0L, active1, 0L);
    }
    
    private int jjMoveStringLiteralDfa11_0(long old1, long active1) {
        if (((active1 &= old1)) == 0L)
            return jjStartNfa_0(9, 0L, old1, 0L);
        try {
            curChar = input_stream.readChar();
        }
        catch (java.io.IOException e) {
            jjStopStringLiteralDfa_0(10, 0L, active1, 0L);
            return 11;
        }
        switch (curChar) {
            case 68:
            case 100:
                if ((active1 & 0x10000L) != 0L)
                    return jjStartNfaWithStates_0(11, 80, 43);
                break;
            default:
                break;
        }
        return jjStartNfa_0(10, 0L, active1, 0L);
    }
    
    private int jjStartNfaWithStates_0(int pos, int kind, int state) {
        jjmatchedKind = kind;
        jjmatchedPos = pos;
        try {
            curChar = input_stream.readChar();
        }
        catch (java.io.IOException e) {
            return pos + 1;
        }
        return jjMoveNfa_0(state, pos + 1);
    }
    
    static final long[] jjbitVec0 =
        {0xfffffffffffffffeL, 0xffffffffffffffffL, 0xffffffffffffffffL, 0xffffffffffffffffL};
    
    static final long[] jjbitVec2 = {0x0L, 0x0L, 0xffffffffffffffffL, 0xffffffffffffffffL};
    
    static final long[] jjbitVec3 = {0x0L, 0x0L, 0x0L, 0x1040001090400010L};
    
    private int jjMoveNfa_0(int startState, int curPos) {
        int startsAt = 0;
        jjnewStateCnt = 43;
        int i = 1;
        jjstateSet[0] = startState;
        int kind = 0x7fffffff;
        for (;;) {
            if (++jjround == 0x7fffffff)
                ReInitRounds();
            if (curChar < 64) {
                long l = 1L << curChar;
                do {
                    switch (jjstateSet[--i]) {
                        case 7:
                            if ((0x3ff000000000000L & l) != 0L) {
                                if (kind > 112)
                                    kind = 112;
                                {
                                    jjCheckNAddStates(0, 6);
                                }
                            }
                            else if (curChar == 34) {
                                jjCheckNAddTwoStates(23, 24);
                            }
                            else if (curChar == 39) {
                                jjCheckNAddTwoStates(18, 19);
                            }
                            else if (curChar == 47)
                                jjstateSet[jjnewStateCnt++] = 8;
                            else if (curChar == 45)
                                jjstateSet[jjnewStateCnt++] = 5;
                            else if (curChar == 46) {
                                jjCheckNAdd(1);
                            }
                            break;
                        case 43:
                        case 16:
                            if ((0x3ff001800000000L & l) == 0L)
                                break;
                            if (kind > 116)
                                kind = 116;
                            {
                                jjCheckNAdd(16);
                            }
                            break;
                        case 0:
                            if (curChar == 46) {
                                jjCheckNAdd(1);
                            }
                            break;
                        case 1:
                            if ((0x3ff000000000000L & l) == 0L)
                                break;
                            if (kind > 111)
                                kind = 111;
                            {
                                jjCheckNAddTwoStates(1, 2);
                            }
                            break;
                        case 3:
                            if ((0x280000000000L & l) != 0L) {
                                jjCheckNAdd(4);
                            }
                            break;
                        case 4:
                            if ((0x3ff000000000000L & l) == 0L)
                                break;
                            if (kind > 111)
                                kind = 111;
                            {
                                jjCheckNAdd(4);
                            }
                            break;
                        case 5:
                            if (curChar != 45)
                                break;
                            if (kind > 114)
                                kind = 114;
                            {
                                jjCheckNAdd(6);
                            }
                            break;
                        case 6:
                            if ((0xffffffffffffdbffL & l) == 0L)
                                break;
                            if (kind > 114)
                                kind = 114;
                            {
                                jjCheckNAdd(6);
                            }
                            break;
                        case 8:
                            if (curChar == 42) {
                                jjCheckNAddTwoStates(9, 10);
                            }
                            break;
                        case 9:
                            if ((0xfffffbffffffffffL & l) != 0L) {
                                jjCheckNAddTwoStates(9, 10);
                            }
                            break;
                        case 10:
                            if (curChar == 42) {
                                jjCheckNAddStates(7, 9);
                            }
                            break;
                        case 11:
                            if ((0xffff7bffffffffffL & l) != 0L) {
                                jjCheckNAddTwoStates(12, 10);
                            }
                            break;
                        case 12:
                            if ((0xfffffbffffffffffL & l) != 0L) {
                                jjCheckNAddTwoStates(12, 10);
                            }
                            break;
                        case 13:
                            if (curChar == 47 && kind > 115)
                                kind = 115;
                            break;
                        case 14:
                            if (curChar == 47)
                                jjstateSet[jjnewStateCnt++] = 8;
                            break;
                        case 17:
                            if (curChar == 39) {
                                jjCheckNAddTwoStates(18, 19);
                            }
                            break;
                        case 18:
                            if ((0xffffff7fffffffffL & l) != 0L) {
                                jjCheckNAddTwoStates(18, 19);
                            }
                            break;
                        case 19:
                            if (curChar != 39)
                                break;
                            if (kind > 119)
                                kind = 119;
                            jjstateSet[jjnewStateCnt++] = 20;
                            break;
                        case 20:
                            if (curChar == 39) {
                                jjCheckNAddTwoStates(21, 19);
                            }
                            break;
                        case 21:
                            if ((0xffffff7fffffffffL & l) != 0L) {
                                jjCheckNAddTwoStates(21, 19);
                            }
                            break;
                        case 22:
                            if (curChar == 34) {
                                jjCheckNAddTwoStates(23, 24);
                            }
                            break;
                        case 23:
                            if ((0xfffffffbffffdbffL & l) != 0L) {
                                jjCheckNAddTwoStates(23, 24);
                            }
                            break;
                        case 24:
                            if (curChar == 34 && kind > 120)
                                kind = 120;
                            break;
                        case 26:
                            if ((0xffffffffffffdbffL & l) != 0L) {
                                jjAddStates(10, 11);
                            }
                            break;
                        case 29:
                            if ((0xffffffffffffdbffL & l) != 0L) {
                                jjAddStates(12, 13);
                            }
                            break;
                        case 31:
                            if ((0x3ff000000000000L & l) == 0L)
                                break;
                            if (kind > 112)
                                kind = 112;
                            {
                                jjCheckNAddStates(0, 6);
                            }
                            break;
                        case 32:
                            if ((0x3ff000000000000L & l) != 0L) {
                                jjCheckNAddTwoStates(32, 0);
                            }
                            break;
                        case 33:
                            if ((0x3ff000000000000L & l) != 0L) {
                                jjCheckNAddTwoStates(33, 34);
                            }
                            break;
                        case 34:
                            if (curChar != 46)
                                break;
                            if (kind > 111)
                                kind = 111;
                            jjstateSet[jjnewStateCnt++] = 35;
                            break;
                        case 36:
                            if ((0x280000000000L & l) != 0L) {
                                jjCheckNAdd(37);
                            }
                            break;
                        case 37:
                            if ((0x3ff000000000000L & l) == 0L)
                                break;
                            if (kind > 111)
                                kind = 111;
                            {
                                jjCheckNAdd(37);
                            }
                            break;
                        case 38:
                            if ((0x3ff000000000000L & l) != 0L) {
                                jjCheckNAddTwoStates(38, 39);
                            }
                            break;
                        case 40:
                            if ((0x280000000000L & l) != 0L) {
                                jjCheckNAdd(41);
                            }
                            break;
                        case 41:
                            if ((0x3ff000000000000L & l) == 0L)
                                break;
                            if (kind > 111)
                                kind = 111;
                            {
                                jjCheckNAdd(41);
                            }
                            break;
                        case 42:
                            if ((0x3ff000000000000L & l) == 0L)
                                break;
                            if (kind > 112)
                                kind = 112;
                            {
                                jjCheckNAdd(42);
                            }
                            break;
                        default:
                            break;
                    }
                } while (i != startsAt);
            }
            else if (curChar < 128) {
                long l = 1L << (curChar & 077);
                do {
                    switch (jjstateSet[--i]) {
                        case 7:
                            if ((0x7fffffe87fffffeL & l) != 0L) {
                                if (kind > 116)
                                    kind = 116;
                                {
                                    jjCheckNAddTwoStates(15, 16);
                                }
                            }
                            else if (curChar == 91) {
                                jjCheckNAddTwoStates(29, 30);
                            }
                            else if (curChar == 96) {
                                jjCheckNAddTwoStates(26, 27);
                            }
                            break;
                        case 43:
                            if ((0x7fffffe87ffffffL & l) != 0L) {
                                if (kind > 116)
                                    kind = 116;
                                {
                                    jjCheckNAdd(16);
                                }
                            }
                            if ((0x7fffffe87fffffeL & l) != 0L) {
                                if (kind > 116)
                                    kind = 116;
                                {
                                    jjCheckNAddTwoStates(15, 16);
                                }
                            }
                            break;
                        case 2:
                            if ((0x2000000020L & l) != 0L) {
                                jjAddStates(14, 15);
                            }
                            break;
                        case 6:
                            if (kind > 114)
                                kind = 114;
                            jjstateSet[jjnewStateCnt++] = 6;
                            break;
                        case 9: {
                            jjCheckNAddTwoStates(9, 10);
                        }
                            break;
                        case 11:
                        case 12: {
                            jjCheckNAddTwoStates(12, 10);
                        }
                            break;
                        case 15:
                            if ((0x7fffffe87fffffeL & l) == 0L)
                                break;
                            if (kind > 116)
                                kind = 116;
                            {
                                jjCheckNAddTwoStates(15, 16);
                            }
                            break;
                        case 16:
                            if ((0x7fffffe87ffffffL & l) == 0L)
                                break;
                            if (kind > 116)
                                kind = 116;
                            {
                                jjCheckNAdd(16);
                            }
                            break;
                        case 18: {
                            jjCheckNAddTwoStates(18, 19);
                        }
                            break;
                        case 21: {
                            jjCheckNAddTwoStates(21, 19);
                        }
                            break;
                        case 23: {
                            jjAddStates(16, 17);
                        }
                            break;
                        case 25:
                            if (curChar == 96) {
                                jjCheckNAddTwoStates(26, 27);
                            }
                            break;
                        case 26:
                            if ((0xfffffffeffffffffL & l) != 0L) {
                                jjCheckNAddTwoStates(26, 27);
                            }
                            break;
                        case 27:
                            if (curChar == 96 && kind > 120)
                                kind = 120;
                            break;
                        case 28:
                            if (curChar == 91) {
                                jjCheckNAddTwoStates(29, 30);
                            }
                            break;
                        case 29:
                            if ((0xffffffffdfffffffL & l) != 0L) {
                                jjCheckNAddTwoStates(29, 30);
                            }
                            break;
                        case 30:
                            if (curChar == 93 && kind > 120)
                                kind = 120;
                            break;
                        case 35:
                            if ((0x2000000020L & l) != 0L) {
                                jjAddStates(18, 19);
                            }
                            break;
                        case 39:
                            if ((0x2000000020L & l) != 0L) {
                                jjAddStates(20, 21);
                            }
                            break;
                        default:
                            break;
                    }
                } while (i != startsAt);
            }
            else {
                int hiByte = (curChar >> 8);
                int i1 = hiByte >> 6;
                long l1 = 1L << (hiByte & 077);
                int i2 = (curChar & 0xff) >> 6;
                long l2 = 1L << (curChar & 077);
                do {
                    switch (jjstateSet[--i]) {
                        case 7:
                        case 15:
                            if (!jjCanMove_1(hiByte, i1, i2, l1, l2))
                                break;
                            if (kind > 116)
                                kind = 116;
                            {
                                jjCheckNAddTwoStates(15, 16);
                            }
                            break;
                        case 43:
                            if (jjCanMove_1(hiByte, i1, i2, l1, l2)) {
                                if (kind > 116)
                                    kind = 116;
                                {
                                    jjCheckNAddTwoStates(15, 16);
                                }
                            }
                            if (jjCanMove_1(hiByte, i1, i2, l1, l2)) {
                                if (kind > 116)
                                    kind = 116;
                                {
                                    jjCheckNAdd(16);
                                }
                            }
                            break;
                        case 6:
                            if (!jjCanMove_0(hiByte, i1, i2, l1, l2))
                                break;
                            if (kind > 114)
                                kind = 114;
                            jjstateSet[jjnewStateCnt++] = 6;
                            break;
                        case 9:
                            if (jjCanMove_0(hiByte, i1, i2, l1, l2)) {
                                jjCheckNAddTwoStates(9, 10);
                            }
                            break;
                        case 11:
                        case 12:
                            if (jjCanMove_0(hiByte, i1, i2, l1, l2)) {
                                jjCheckNAddTwoStates(12, 10);
                            }
                            break;
                        case 16:
                            if (!jjCanMove_1(hiByte, i1, i2, l1, l2))
                                break;
                            if (kind > 116)
                                kind = 116;
                            {
                                jjCheckNAdd(16);
                            }
                            break;
                        case 18:
                            if (jjCanMove_0(hiByte, i1, i2, l1, l2)) {
                                jjCheckNAddTwoStates(18, 19);
                            }
                            break;
                        case 21:
                            if (jjCanMove_0(hiByte, i1, i2, l1, l2)) {
                                jjCheckNAddTwoStates(21, 19);
                            }
                            break;
                        case 23:
                            if (jjCanMove_0(hiByte, i1, i2, l1, l2)) {
                                jjAddStates(16, 17);
                            }
                            break;
                        case 26:
                            if (jjCanMove_0(hiByte, i1, i2, l1, l2)) {
                                jjAddStates(10, 11);
                            }
                            break;
                        case 29:
                            if (jjCanMove_0(hiByte, i1, i2, l1, l2)) {
                                jjAddStates(12, 13);
                            }
                            break;
                        default:
                            if (i1 == 0 || l1 == 0 || i2 == 0 || l2 == 0)
                                break;
                            else
                                break;
                    }
                } while (i != startsAt);
            }
            if (kind != 0x7fffffff) {
                jjmatchedKind = kind;
                jjmatchedPos = curPos;
                kind = 0x7fffffff;
            }
            ++curPos;
            if ((i = jjnewStateCnt) == (startsAt = 43 - (jjnewStateCnt = startsAt)))
                return curPos;
            try {
                curChar = input_stream.readChar();
            }
            catch (java.io.IOException e) {
                return curPos;
            }
        }
    }
    
    static final int[] jjnextStates = {32, 0, 33, 34, 38, 39, 42, 10, 11, 13, 26, 27, 29, 30, 3, 4, 23, 24, 36, 37, 40,
        41,};
    
    private static final boolean jjCanMove_0(int hiByte, int i1, int i2, long l1, long l2) {
        switch (hiByte) {
            case 0:
                return ((jjbitVec2[i2] & l2) != 0L);
            default:
                if ((jjbitVec0[i1] & l1) != 0L)
                    return true;
                return false;
        }
    }
    
    private static final boolean jjCanMove_1(int hiByte, int i1, int i2, long l1, long l2) {
        switch (hiByte) {
            case 0:
                return ((jjbitVec3[i2] & l2) != 0L);
            default:
                return false;
        }
    }
    
    /** Token literal values. */
    public static final String[] jjstrLiteralImages = {"", null, null, null, null, null, null, null, null, null, null,
        null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
        null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
        null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
        null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
        null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
        null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
        null, null, null, "\73", "\54", "\75", "\50", "\51", "\52", "\56", "\77", "\50\53\51", "\76", "\74", "\76\75",
        "\74\75", "\74\76", "\41\75", "\100\100", "\176", "\176\52", "\41\176", "\41\176\52", "\174\174", "\174",
        "\46", "\53", "\55", "\57", "\45", "\136", null, "\175", null, null, "\72\72", "\72", "\55\76", null,};
    
    protected Token jjFillToken() {
        final Token t;
        final String curTokenImage;
        final int beginLine;
        final int endLine;
        final int beginColumn;
        final int endColumn;
        String im = jjstrLiteralImages[jjmatchedKind];
        curTokenImage = (im == null) ? input_stream.GetImage() : im;
        beginLine = input_stream.getBeginLine();
        beginColumn = input_stream.getBeginColumn();
        endLine = input_stream.getEndLine();
        endColumn = input_stream.getEndColumn();
        t = Token.newToken(jjmatchedKind, curTokenImage);
        
        t.beginLine = beginLine;
        t.endLine = endLine;
        t.beginColumn = beginColumn;
        t.endColumn = endColumn;
        
        return t;
    }
    
    int curLexState = 0;
    
    int defaultLexState = 0;
    
    int jjnewStateCnt;
    
    int jjround;
    
    int jjmatchedPos;
    
    int jjmatchedKind;
    
    /** Get the next Token. */
    public Token getNextToken() {
        Token specialToken = null;
        Token matchedToken;
        int curPos = 0;
        
        EOFLoop: for (;;) {
            try {
                curChar = input_stream.BeginToken();
            }
            catch (Exception e) {
                jjmatchedKind = 0;
                jjmatchedPos = -1;
                matchedToken = jjFillToken();
                matchedToken.specialToken = specialToken;
                return matchedToken;
            }
            
            try {
                input_stream.backup(0);
                while (curChar <= 32 && (0x100002600L & (1L << curChar)) != 0L)
                    curChar = input_stream.BeginToken();
            }
            catch (java.io.IOException e1) {
                continue EOFLoop;
            }
            jjmatchedKind = 0x7fffffff;
            jjmatchedPos = 0;
            curPos = jjMoveStringLiteralDfa0_0();
            if (jjmatchedKind != 0x7fffffff) {
                if (jjmatchedPos + 1 < curPos)
                    input_stream.backup(curPos - jjmatchedPos - 1);
                if ((jjtoToken[jjmatchedKind >> 6] & (1L << (jjmatchedKind & 077))) != 0L) {
                    matchedToken = jjFillToken();
                    matchedToken.specialToken = specialToken;
                    return matchedToken;
                }
                else {
                    if ((jjtoSpecial[jjmatchedKind >> 6] & (1L << (jjmatchedKind & 077))) != 0L) {
                        matchedToken = jjFillToken();
                        if (specialToken == null)
                            specialToken = matchedToken;
                        else {
                            matchedToken.specialToken = specialToken;
                            specialToken = (specialToken.next = matchedToken);
                        }
                    }
                    continue EOFLoop;
                }
            }
            int error_line = input_stream.getEndLine();
            int error_column = input_stream.getEndColumn();
            String error_after = null;
            boolean EOFSeen = false;
            try {
                input_stream.readChar();
                input_stream.backup(1);
            }
            catch (java.io.IOException e1) {
                EOFSeen = true;
                error_after = curPos <= 1 ? "" : input_stream.GetImage();
                if (curChar == '\n' || curChar == '\r') {
                    error_line++;
                    error_column = 0;
                }
                else
                    error_column++;
            }
            if (!EOFSeen) {
                input_stream.backup(1);
                error_after = curPos <= 1 ? "" : input_stream.GetImage();
            }
            throw new TokenMgrError(EOFSeen, curLexState, error_line, error_column, error_after, curChar,
                TokenMgrError.LEXICAL_ERROR);
        }
    }
    
    private void jjCheckNAdd(int state) {
        if (jjrounds[state] != jjround) {
            jjstateSet[jjnewStateCnt++] = state;
            jjrounds[state] = jjround;
        }
    }
    
    private void jjAddStates(int start, int end) {
        do {
            jjstateSet[jjnewStateCnt++] = jjnextStates[start];
        } while (start++ != end);
    }
    
    private void jjCheckNAddTwoStates(int state1, int state2) {
        jjCheckNAdd(state1);
        jjCheckNAdd(state2);
    }
    
    private void jjCheckNAddStates(int start, int end) {
        do {
            jjCheckNAdd(jjnextStates[start]);
        } while (start++ != end);
    }
    
    /** Constructor. */
    public CCJSqlParserTokenManager(SimpleCharStream stream) {
        
        if (SimpleCharStream.staticFlag)
            throw new Error("ERROR: Cannot use a static CharStream class with a non-static lexical analyzer.");
        
        input_stream = stream;
    }
    
    /** Constructor. */
    public CCJSqlParserTokenManager(SimpleCharStream stream, int lexState) {
        ReInit(stream);
        SwitchTo(lexState);
    }
    
    /** Reinitialise parser. */
    public void ReInit(SimpleCharStream stream) {
        
        jjmatchedPos = jjnewStateCnt = 0;
        curLexState = defaultLexState;
        input_stream = stream;
        ReInitRounds();
    }
    
    private void ReInitRounds() {
        int i;
        jjround = 0x80000001;
        for (i = 43; i-- > 0;)
            jjrounds[i] = 0x80000000;
    }
    
    /** Reinitialise parser. */
    public void ReInit(SimpleCharStream stream, int lexState) {
        
        ReInit(stream);
        SwitchTo(lexState);
    }
    
    /** Switch to specified lex state. */
    public void SwitchTo(int lexState) {
        if (lexState >= 1 || lexState < 0)
            throw new TokenMgrError("Error: Ignoring invalid lexical state : " + lexState + ". State unchanged.",
                TokenMgrError.INVALID_LEXICAL_STATE);
        else
            curLexState = lexState;
    }
    
    /** Lexer state names. */
    public static final String[] lexStateNames = {"DEFAULT",};
    
    static final long[] jjtoToken = {0xffffffffffffffe1L, 0xfd91ffffffffffffL, 0x3fffffffL,};
    
    static final long[] jjtoSkip = {0x1eL, 0xc000000000000L, 0x0L,};
    
    static final long[] jjtoSpecial = {0x0L, 0xc000000000000L, 0x0L,};
    
    protected SimpleCharStream input_stream;
    
    private final int[] jjrounds = new int[43];
    
    private final int[] jjstateSet = new int[2 * 43];
    
    protected int curChar;
}

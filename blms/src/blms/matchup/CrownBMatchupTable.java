package blms.matchup;

import java.util.LinkedList;
import java.util.List;

public class CrownBMatchupTable implements IMatchupTable {

	public static final CrownBMatchupTable instance = new CrownBMatchupTable();
	List<SubTable> subTables;
	
	private CrownBMatchupTable() {
		subTables = new LinkedList<SubTable>();
		
		SubTable table50 = new SubTable(50);
		table50.addRange(0,7,50);
		table50.addRange(8,23,45);
		table50.addRange(24,41,40);
		table50.addRange(42,62,35);
		table50.addRange(63,86,30);
		table50.addRange(87,115,25);
		table50.addRange(116,151,20);
		table50.addRange(152,200,15);
		table50.addRange(201,273,10);
		subTables.add(table50);
		
		SubTable table60 = new SubTable(60);
		table60.addRange(0,6,60);
		table60.addRange(7,19,55);
		table60.addRange(20,33,50);
		table60.addRange(34,49,45);
		table60.addRange(50,67,40);
		table60.addRange(68,88,35);
		table60.addRange(89,112,30);
		table60.addRange(113,141,25);
		table60.addRange(142,177,20);
		table60.addRange(178,226,15);
		table60.addRange(227,300,10);
		subTables.add(table60);
		
		SubTable table70 = new SubTable(70);
		table70.addRange(0,5,70);
		table70.addRange(6,16,65);
		table70.addRange(17,28,60);
		table70.addRange(29,41,55);
		table70.addRange(42,55,50);
		table70.addRange(56,71,45);
		table70.addRange(72,90,40);
		table70.addRange(91,110,35);
		table70.addRange(111,134,30);
		table70.addRange(135,163,25);
		table70.addRange(164,200,20);
		table70.addRange(201,248,15);
		table70.addRange(249,322,10);
		subTables.add(table70);
		
		SubTable table80 = new SubTable(80);
		table80.addRange(0,4,80);
		table80.addRange(5,14,75);
		table80.addRange(15,24,70);
		table80.addRange(25,35,65);
		table80.addRange(36,47,60);
		table80.addRange(48,60,55);
		table80.addRange(61,75,50);
		table80.addRange(76,91,45);
		table80.addRange(92,109,40);
		table80.addRange(110,129,35);
		table80.addRange(130,154,30);
		table80.addRange(155,183,25);
		table80.addRange(184,219,20);
		table80.addRange(220,267,15);
		table80.addRange(268,341,10);
		subTables.add(table80);
		
		SubTable table90 = new SubTable(90);
		table90.addRange(0,4,90);
		table90.addRange(5,12,85);
		table90.addRange(13,21,80);
		table90.addRange(22,31,75);
		table90.addRange(32,41,70);
		table90.addRange(42,52,65);
		table90.addRange(53,64,60);
		table90.addRange(65,77,55);
		table90.addRange(78,92,50);
		table90.addRange(93,108,45);
		table90.addRange(109,126,40);
		table90.addRange(127,146,35);
		table90.addRange(147,171,30);
		table90.addRange(172,200,25);
		table90.addRange(201,236,20);
		table90.addRange(237,284,15);
		table90.addRange(285,358,10);
		subTables.add(table90);
		
		SubTable table100 = new SubTable(100);
		table100.addRange(0,3,100);
		table100.addRange(4,11,95);
		table100.addRange(12,19,90);
		table100.addRange(20,27,85);
		table100.addRange(28,36,80);
		table100.addRange(37,46,75);
		table100.addRange(47,56,70);
		table100.addRange(57,67,65);
		table100.addRange(68,79,60);
		table100.addRange(80,92,55);
		table100.addRange(93,107,50);
		table100.addRange(108,123,45);
		table100.addRange(124,141,40);
		table100.addRange(142,162,35);
		table100.addRange(163,186,30);
		table100.addRange(187,215,25);
		table100.addRange(216,251,20);
		table100.addRange(252,300,15);
		table100.addRange(301,373,10);
		subTables.add(table100);
		
		SubTable table110 = new SubTable(110);
		table110.addRange(0,3,110);
		table110.addRange(4,10,105);
		table110.addRange(11,17,100);
		table110.addRange(18,24,95);
		table110.addRange(25,33,90);
		table110.addRange(34,41,85);
		table110.addRange(42,50,80);
		table110.addRange(51,60,75);
		table110.addRange(61,70,70);
		table110.addRange(71,81,65);
		table110.addRange(82,93,60);
		table110.addRange(94,106,55);
		table110.addRange(107,121,50);
		table110.addRange(122,137,45);
		table110.addRange(138,155,40);
		table110.addRange(156,175,35);
		table110.addRange(176,200,30);
		table110.addRange(201,228,25);
		table110.addRange(229,265,20);
		table110.addRange(266,313,15);
		table110.addRange(314,387,10);
		subTables.add(table110);
		
		SubTable table120 = new SubTable(120);
		table120.addRange(0,3,120);
		table120.addRange(4,9,115);
		table120.addRange(10,15,110);
		table120.addRange(16,22,105);
		table120.addRange(23,29,100);
		table120.addRange(30,37,95);
		table120.addRange(38,45,90);
		table120.addRange(46,54,85);
		table120.addRange(55,63,80);
		table120.addRange(64,72,75);
		table120.addRange(73,83,70);
		table120.addRange(84,94,65);
		table120.addRange(95,106,60);
		table120.addRange(107,119,55);
		table120.addRange(120,133,50);
		table120.addRange(134,149,45);
		table120.addRange(150,167,40);
		table120.addRange(168,188,35);
		table120.addRange(189,212,30);
		table120.addRange(213,241,25);
		table120.addRange(242,277,20);
		table120.addRange(278,326,15);
		table120.addRange(327,400,10);
		subTables.add(table120);
		
		SubTable table130 = new SubTable(130);
		table130.addRange(0,2,130);
		table130.addRange(3,8,125);
		table130.addRange(9,14,120);
		table130.addRange(15,20,115);
		table130.addRange(21,27,110);
		table130.addRange(28,34,105);
		table130.addRange(35,41,100);
		table130.addRange(42,49,95);
		table130.addRange(50,57,90);
		table130.addRange(58,65,85);
		table130.addRange(66,74,80);
		table130.addRange(75,84,75);
		table130.addRange(85,94,70);
		table130.addRange(95,105,65);
		table130.addRange(106,117,60);
		table130.addRange(118,130,55);
		table130.addRange(131,145,50);
		table130.addRange(146,161,45);
		table130.addRange(162,179,40);
		table130.addRange(180,200,35);
		table130.addRange(201,224,30);
		table130.addRange(225,253,25);
		table130.addRange(254,289,20);
		table130.addRange(290,337,15);
		table130.addRange(338,411,10);
		subTables.add(table130);
		
		SubTable table140 = new SubTable(140);
		table140.addRange(0,2,140);
		table140.addRange(3,7,135);
		table140.addRange(8,13,130);
		table140.addRange(14,19,125);
		table140.addRange(20,25,120);
		table140.addRange(26,31,115);
		table140.addRange(32,38,110);
		table140.addRange(39,44,105);
		table140.addRange(45,52,100);
		table140.addRange(53,59,95);
		table140.addRange(60,67,90);
		table140.addRange(68,76,85);
		table140.addRange(77,85,80);
		table140.addRange(86,94,75);
		table140.addRange(95,105,70);
		table140.addRange(106,116,65);
		table140.addRange(117,128,60);
		table140.addRange(129,141,55);
		table140.addRange(142,155,50);
		table140.addRange(156,171,45);
		table140.addRange(172,190,40);
		table140.addRange(191,210,35);
		table140.addRange(211,234,30);
		table140.addRange(235,263,25);
		table140.addRange(264,300,20);
		table140.addRange(301,348,15);
		table140.addRange(349,422,10);
		subTables.add(table140);
		
		SubTable table150 = new SubTable(150);
		table150.addRange(0,2,150);
		table150.addRange(3,7,145);
		table150.addRange(8,12,140);
		table150.addRange(13,17,135);
		table150.addRange(18,23,130);
		table150.addRange(24,29,125);
		table150.addRange(30,35,120);
		table150.addRange(36,41,115);
		table150.addRange(42,48,110);
		table150.addRange(49,54,105);
		table150.addRange(55,62,100);
		table150.addRange(63,69,95);
		table150.addRange(70,77,90);
		table150.addRange(78,86,85);
		table150.addRange(87,95,80);
		table150.addRange(96,104,75);
		table150.addRange(105,117,70);
		table150.addRange(116,126,65);
		table150.addRange(127,138,60);
		table150.addRange(139,151,55);
		table150.addRange(152,165,50);
		table150.addRange(166,181,45);
		table150.addRange(182,200,40);
		table150.addRange(201,220,35);
		table150.addRange(221,244,30);
		table150.addRange(245,273,25);
		table150.addRange(274,309,20);
		table150.addRange(310,358,15);
		table150.addRange(359,432,10);
		subTables.add(table150);
		
	}

	public static CrownBMatchupTable getInstance() {
		return instance;
	}

	@Override
	public int getW(int handicapDifference, int winnerScore) {
		
		if(winnerScore <= 0) {
			throw new RuntimeException("Invalid Score");
		}
		for (SubTable subtable : subTables) {
			if(subtable.winnerScore == winnerScore) {
				for(Range range : subtable.ranges) {
					if(handicapDifference >= range.minValue && handicapDifference <= range.maxValue) {
						return range.w;
					}
					
				}
				
			}
		}
		
		throw new RuntimeException("Invalid Score - the score must be one of the following values 50, 60, 70, 80, 90, 100, 110, 120, 130, 140, 150");
	}

}

class SubTable {
	int winnerScore;
	List<Range> ranges;
	
	public SubTable(int winnerScore) {
		this.winnerScore = winnerScore;
		ranges = new LinkedList<Range>();
	}
	
	public void addRange(int minValue, int maxValue, int w) {
		Range range = new Range(minValue, maxValue, w);
		this.ranges.add(range);
	}
}

class Range {
	int minValue;
	int maxValue;
	int w;
	
	public Range(int minValue, int maxValue, int w) {
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.w = w;
	}

	public int getMinValue() {
		return minValue;
	}

	public void setMinValue(int minValue) {
		this.minValue = minValue;
	}

	public int getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(int maxValue) {
		this.maxValue = maxValue;
	}

	public int getW() {
		return w;
	}

	public void setW(int w) {
		this.w = w;
	}
}
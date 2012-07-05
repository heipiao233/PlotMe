package com.worldcretornica.plotme;

import java.util.HashMap;
import java.util.List;

public class PlotMapInfo {

	public int PlotSize;
	public int PlotAutoLimit;
	public int PathWidth;
	public byte BottomBlockId;
	public byte WallBlockId;
	public byte PlotFloorBlockId;
	public byte PlotFillingBlockId;
	public HashMap<String, Plot> plots;
	public int RoadHeight;
	public int DaysToExpiration;
	
	public String ProtectedWallBlockId;
	public String ForSaleWallBlockId;
	public String AuctionWallBlockId;
	
	public List<Integer> ProtectedBlocks;
	
	public List<String> PreventedItems;

	public boolean UseEconomy;
	public boolean CanPutOnSale;
	public boolean CanSellToBank;
	public boolean RefundClaimPriceOnReset;
	public boolean RefundClaimPriceOnSetOwner;
	public double ClaimPrice;
	public double ClearPrice;
	public double AddPlayerPrice;
	public double RemovePlayerPrice;
	public double PlotHomePrice;
	public boolean CanCustomizeSellPrice;
	public double SellToPlayerPrice;
	public double SellToBankPrice;
	public double BuyFromBankPrice;
	public double AddCommentPrice;
	public double BiomeChangePrice;
	public double ProtectPrice;
	
}

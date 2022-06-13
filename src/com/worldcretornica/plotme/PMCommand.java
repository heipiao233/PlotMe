package com.worldcretornica.plotme;

import org.bukkit.block.Biome;
import org.apache.commons.lang.StringUtils;
import java.util.UUID;
import java.util.Date;
import java.util.Calendar;
import java.util.HashMap;
import com.worldcretornica.plotme.utils.MinecraftFontWidthCalculator;
import java.util.List;
import java.util.Collections;
import java.util.ArrayList;
import org.bukkit.Location;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.OfflinePlayer;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;

public class PMCommand implements CommandExecutor {
    private PlotMe plugin;
    private final ChatColor BLUE;
    private final ChatColor RED;
    private final ChatColor RESET;
    private final ChatColor AQUA;
    private final ChatColor GREEN;
    private final ChatColor ITALIC;
    private final String PREFIX;
    private final String LOG;
    private final boolean isAdv;
    
    public PMCommand(final PlotMe instance) {
        this.BLUE = ChatColor.BLUE;
        this.RED = ChatColor.RED;
        this.RESET = ChatColor.RESET;
        this.AQUA = ChatColor.AQUA;
        this.GREEN = ChatColor.GREEN;
        this.ITALIC = ChatColor.ITALIC;
        this.PREFIX = PlotMe.PREFIX;
        this.LOG = "[" + PlotMe.NAME + " Event] ";
        this.isAdv = PlotMe.advancedlogging;
        this.plugin = instance;
    }
    
    private String C(final String caption) {
        return PlotMe.caption(caption);
    }
    
    public boolean onCommand(final CommandSender s, final Command c, final String l, final String[] args) {
        if (l.equalsIgnoreCase("plotme") || l.equalsIgnoreCase("plot") || l.equalsIgnoreCase("p")) {
            if (!(s instanceof Player)) {
                if (args.length == 0 || args[0].equalsIgnoreCase("1")) {
                    s.sendMessage(this.C("ConsoleHelpMain"));
                    s.sendMessage(" - /plotme reload");
                    s.sendMessage(this.C("ConsoleHelpReload"));
                    return true;
                }
                final String a0 = args[0];
                if (a0.equalsIgnoreCase("reload")) {
                    return this.reload(s, args);
                }
                if (a0.equalsIgnoreCase(this.C("CommandResetExpired"))) {
                    return this.resetexpired(s, args);
                }
            }
            else {
                final Player p = (Player)s;
                if (args.length == 0) {
                    return this.showhelp(p, 1);
                }
                final String a2 = args[0];
                int ipage = -1;
                try {
                    ipage = Integer.parseInt(a2);
                }
                catch (NumberFormatException ex) {}
                if (ipage != -1) {
                    return this.showhelp(p, ipage);
                }
                if (a2.equalsIgnoreCase(this.C("CommandHelp"))) {
                    ipage = -1;
                    if (args.length > 1) {
                        final String a3 = args[1];
                        ipage = -1;
                        try {
                            ipage = Integer.parseInt(a3);
                        }
                        catch (NumberFormatException ex2) {}
                    }
                    if (ipage != -1) {
                        return this.showhelp(p, ipage);
                    }
                    return this.showhelp(p, 1);
                }
                else {
                    if (a2.equalsIgnoreCase(this.C("CommandClaim"))) {
                        return this.claim(p, args);
                    }
                    if (a2.equalsIgnoreCase(this.C("CommandAuto"))) {
                        return this.auto(p, args);
                    }
                    if (a2.equalsIgnoreCase(this.C("CommandInfo")) || a2.equalsIgnoreCase("i")) {
                        return this.info(p, args);
                    }
                    if (a2.equalsIgnoreCase(this.C("CommandComment"))) {
                        return this.comment(p, args);
                    }
                    if (a2.equalsIgnoreCase(this.C("CommandComments")) || a2.equalsIgnoreCase("c")) {
                        return this.comments(p, args);
                    }
                    if (a2.equalsIgnoreCase(this.C("CommandBiome")) || a2.equalsIgnoreCase("b")) {
                        return this.biome(p, args);
                    }
                    if (a2.equalsIgnoreCase(this.C("CommandBiomelist"))) {
                        return this.biomelist(p, args);
                    }
                    if (a2.equalsIgnoreCase(this.C("CommandId"))) {
                        return this.id(p, args);
                    }
                    if (a2.equalsIgnoreCase(this.C("CommandTp"))) {
                        return this.tp(p, args);
                    }
                    if (a2.equalsIgnoreCase(this.C("CommandClear"))) {
                        return this.clear(p, args);
                    }
                    if (a2.equalsIgnoreCase(this.C("CommandReset"))) {
                        return this.reset(p, args);
                    }
                    if (a2.equalsIgnoreCase(this.C("CommandAdd")) || a2.equalsIgnoreCase("+")) {
                        return this.add(p, args);
                    }
                    if (PlotMe.allowToDeny) {
                        if (a2.equalsIgnoreCase(this.C("CommandDeny"))) {
                            return this.deny(p, args);
                        }
                        if (a2.equalsIgnoreCase(this.C("CommandUndeny"))) {
                            return this.undeny(p, args);
                        }
                    }
                    if (a2.equalsIgnoreCase(this.C("CommandRemove")) || a2.equalsIgnoreCase("-")) {
                        return this.remove(p, args);
                    }
                    if (a2.equalsIgnoreCase(this.C("CommandSetowner")) || a2.equalsIgnoreCase("o")) {
                        return this.setowner(p, args);
                    }
                    if (a2.equalsIgnoreCase(this.C("CommandMove")) || a2.equalsIgnoreCase("m")) {
                        return this.move(p, args);
                    }
                    if (a2.equalsIgnoreCase("reload")) {
                        return this.reload(s, args);
                    }
                    if (a2.equalsIgnoreCase(this.C("CommandWEAnywhere"))) {
                        return this.weanywhere(p, args);
                    }
                    if (a2.equalsIgnoreCase(this.C("CommandList"))) {
                        return this.plotlist(p, args);
                    }
                    if (a2.equalsIgnoreCase(this.C("CommandExpired"))) {
                        return this.expired(p, args);
                    }
                    if (a2.equalsIgnoreCase(this.C("CommandAddtime"))) {
                        return this.addtime(p, args);
                    }
                    if (a2.equalsIgnoreCase(this.C("CommandDone"))) {
                        return this.done(p, args);
                    }
                    if (a2.equalsIgnoreCase(this.C("CommandDoneList"))) {
                        return this.donelist(p, args);
                    }
                    if (a2.equalsIgnoreCase(this.C("CommandProtect"))) {
                        return this.protect(p, args);
                    }
                    if (a2.equalsIgnoreCase(this.C("CommandSell"))) {
                        return this.sell(p, args);
                    }
                    if (a2.equalsIgnoreCase(this.C("CommandDispose"))) {
                        return this.dispose(p, args);
                    }
                    if (a2.equalsIgnoreCase(this.C("CommandAuction"))) {
                        return this.auction(p, args);
                    }
                    if (a2.equalsIgnoreCase(this.C("CommandBuy"))) {
                        return this.buy(p, args);
                    }
                    if (a2.equalsIgnoreCase(this.C("CommandBid"))) {
                        return this.bid(p, args);
                    }
                    if (a2.startsWith(this.C("CommandHome")) || a2.startsWith("h")) {
                        return this.home(p, args);
                    }
                    if (a2.equalsIgnoreCase(this.C("CommandResetExpired"))) {
                        return this.resetexpired(p, args);
                    }
                }
            }
        }
        return false;
    }
    
    private boolean resetexpired(final CommandSender s, final String[] args) {
        if (PlotMe.cPerms(s, "PlotMe.admin.resetexpired")) {
            if (args.length <= 1) {
                this.Send(s, this.C("WordUsage") + ": " + this.RED + "/plotme " + this.C("CommandResetExpired") + " <" + this.C("WordWorld") + "> " + this.RESET + "Example: " + this.RED + "/plotme " + this.C("CommandResetExpired") + " plotworld ");
            }
            else if (PlotMe.worldcurrentlyprocessingexpired != null) {
                this.Send(s, PlotMe.cscurrentlyprocessingexpired.getName() + " " + this.C("MsgAlreadyProcessingPlots"));
            }
            else {
                final World w = s.getServer().getWorld(args[1]);
                if (w == null) {
                    this.Send(s, this.RED + this.C("WordWorld") + " '" + args[1] + "' " + this.C("MsgDoesNotExistOrNotLoaded"));
                    return true;
                }
                if (!PlotManager.isPlotWorld(w)) {
                    this.Send(s, this.RED + this.C("MsgNotPlotWorld"));
                    return true;
                }
                PlotMe.worldcurrentlyprocessingexpired = w;
                PlotMe.cscurrentlyprocessingexpired = s;
                PlotMe.counterexpired = 50;
                PlotMe.nbperdeletionprocessingexpired = 5;
                this.plugin.scheduleTask(new PlotRunnableDeleteExpire(), 5, 50);
            }
        }
        else {
            this.Send(s, this.RED + this.C("MsgPermissionDenied"));
        }
        return true;
    }
    
    private boolean bid(final Player p, final String[] args) {
        if (PlotManager.isEconomyEnabled(p)) {
            if (PlotMe.cPerms(p, "PlotMe.use.bid")) {
                final String id = PlotManager.getPlotId(p.getLocation());
                if (id.equals("")) {
                    this.Send(p, this.RED + this.C("MsgNoPlotFound"));
                }
                else if (!PlotManager.isPlotAvailable(id, p)) {
                    final Plot plot = PlotManager.getPlotById(p, id);
                    if (plot.auctionned) {
                        final String bidder = p.getName();
                        final OfflinePlayer playerbidder = p;
                        if (plot.owner.equalsIgnoreCase(bidder)) {
                            this.Send(p, this.RED + this.C("MsgCannotBidOwnPlot"));
                        }
                        else if (args.length == 2) {
                            double bid = 0.0;
                            final double currentbid = plot.currentbid;
                            final String currentbidder = plot.currentbidder;
                            final OfflinePlayer playercurrentbidder = Bukkit.getOfflinePlayer(plot.currentbidderId);
                            try {
                                bid = Double.parseDouble(args[1]);
                            }
                            catch (NumberFormatException ex) {}
                            final boolean equals = currentbidder.equals("");
                            if (bid < currentbid) {
                                this.Send(p, this.RED + this.C("MsgInvalidBidMustBeAbove") + " " + this.RESET + this.f(plot.currentbid, false));
                            }
                            else if (bid == currentbid) {
                                if (!equals) {
                                    this.Send(p, this.RED + this.C("MsgInvalidBidMustBeAbove") + " " + this.RESET + this.f(plot.currentbid, false));
                                }
                                else {
                                    final double balance = PlotMe.economy.getBalance(playerbidder);
                                    if ((bid >= balance && !currentbidder.equals(bidder)) || (currentbidder.equals(bidder) && bid > balance + currentbid)) {
                                        this.Send(p, this.RED + this.C("MsgNotEnoughBid"));
                                    }
                                    else {
                                        final EconomyResponse er = PlotMe.economy.withdrawPlayer(playerbidder, bid);
                                        if (er.transactionSuccess()) {
                                            plot.currentbidder = bidder;
                                            plot.currentbid = bid;
                                            plot.updateField("currentbidder", bidder);
                                            plot.updateField("currentbid", bid);
                                            PlotManager.setSellSign(p.getWorld(), plot);
                                            this.Send(p, this.C("MsgBidAccepted") + " " + this.f(-bid));
                                            if (this.isAdv) {
                                                PlotMe.logger.info(this.LOG + bidder + " bid " + bid + " on plot " + id);
                                            }
                                        }
                                        else {
                                            this.Send(p, er.errorMessage);
                                            this.warn(er.errorMessage);
                                        }
                                    }
                                }
                            }
                            else {
                                final double balance = PlotMe.economy.getBalance(playerbidder);
                                if ((bid >= balance && !currentbidder.equals(bidder)) || (currentbidder.equals(bidder) && bid > balance + currentbid)) {
                                    this.Send(p, this.RED + this.C("MsgNotEnoughBid"));
                                }
                                else {
                                    final EconomyResponse er = PlotMe.economy.withdrawPlayer(playerbidder, bid);
                                    if (er.transactionSuccess()) {
                                        if (!equals) {
                                            final EconomyResponse er2 = PlotMe.economy.depositPlayer(playercurrentbidder, currentbid);
                                            if (!er2.transactionSuccess()) {
                                                this.Send(p, er2.errorMessage);
                                                this.warn(er2.errorMessage);
                                            }
                                            else {
                                                for (final Player player : Bukkit.getServer().getOnlinePlayers()) {
                                                    if (player.getName().equalsIgnoreCase(currentbidder)) {
                                                        this.Send(player, this.C("MsgOutbidOnPlot") + " " + id + " " + this.C("MsgOwnedBy") + " " + plot.owner + ". " + this.f(bid));
                                                        break;
                                                    }
                                                }
                                            }
                                        }
                                        plot.currentbidder = bidder;
                                        plot.currentbid = bid;
                                        plot.updateField("currentbidder", bidder);
                                        plot.updateField("currentbid", bid);
                                        PlotManager.setSellSign(p.getWorld(), plot);
                                        this.Send(p, this.C("MsgBidAccepted") + " " + this.f(-bid));
                                        if (this.isAdv) {
                                            PlotMe.logger.info(this.LOG + bidder + " bid " + bid + " on plot " + id);
                                        }
                                    }
                                    else {
                                        this.Send(p, er.errorMessage);
                                        this.warn(er.errorMessage);
                                    }
                                }
                            }
                        }
                        else {
                            this.Send(p, this.C("WordUsage") + ": " + this.RED + "/plotme " + this.C("CommandBid") + " <" + this.C("WordAmount") + "> " + this.RESET + this.C("WordExample") + ": " + this.RED + "/plotme " + this.C("CommandBid") + " 100");
                        }
                    }
                    else {
                        this.Send(p, this.RED + this.C("MsgPlotNotAuctionned"));
                    }
                }
                else {
                    this.Send(p, this.RED + this.C("MsgThisPlot") + "(" + id + ") " + this.C("MsgHasNoOwner"));
                }
            }
            else {
                this.Send(p, this.RED + this.C("MsgPermissionDenied"));
            }
        }
        else {
            this.Send(p, this.RED + this.C("MsgEconomyDisabledWorld"));
        }
        return true;
    }
    
    private boolean buy(final Player p, final String[] args) {
        if (PlotManager.isEconomyEnabled(p)) {
            if (PlotMe.cPerms(p, "PlotMe.use.buy") || PlotMe.cPerms(p, "PlotMe.admin.buy")) {
                final Location l = p.getLocation();
                final String id = PlotManager.getPlotId(l);
                if (id.equals("")) {
                    this.Send(p, this.RED + this.C("MsgNoPlotFound"));
                }
                else if (!PlotManager.isPlotAvailable(id, p)) {
                    final Plot plot = PlotManager.getPlotById(p, id);
                    if (!plot.forsale) {
                        this.Send(p, this.RED + this.C("MsgPlotNotForSale"));
                    }
                    else {
                        final String buyer = p.getName();
                        if (plot.owner.equalsIgnoreCase(buyer)) {
                            this.Send(p, this.RED + this.C("MsgCannotBuyOwnPlot"));
                        }
                        else {
                            final int plotlimit = PlotMe.getPlotLimit(p);
                            if (plotlimit != -1 && PlotManager.getNbOwnedPlot(p) >= plotlimit) {
                                this.Send(p, this.C("MsgAlreadyReachedMaxPlots") + " (" + PlotManager.getNbOwnedPlot(p) + "/" + PlotMe.getPlotLimit(p) + "). " + this.C("WordUse") + " " + this.RED + "/plotme " + this.C("CommandHome") + this.RESET + " " + this.C("MsgToGetToIt"));
                            }
                            else {
                                final World w = p.getWorld();
                                final double cost = plot.customprice;
                                if (PlotMe.economy.getBalance(p) < cost) {
                                    this.Send(p, this.RED + this.C("MsgNotEnoughBuy"));
                                }
                                else {
                                    final EconomyResponse er = PlotMe.economy.withdrawPlayer(p, cost);
                                    if (er.transactionSuccess()) {
                                        final String oldowner = plot.owner;
                                        final OfflinePlayer playercurrentbidder = Bukkit.getOfflinePlayer(plot.ownerId);
                                        if (!oldowner.equalsIgnoreCase("$Bank$")) {
                                            final EconomyResponse er2 = PlotMe.economy.depositPlayer(playercurrentbidder, cost);
                                            if (!er2.transactionSuccess()) {
                                                this.Send(p, this.RED + er2.errorMessage);
                                                this.warn(er2.errorMessage);
                                            }
                                            else {
                                                for (final Player player : Bukkit.getServer().getOnlinePlayers()) {
                                                    if (player.getName().equalsIgnoreCase(oldowner)) {
                                                        this.Send(player, this.C("WordPlot") + " " + id + " " + this.C("MsgSoldTo") + " " + buyer + ". " + this.f(cost));
                                                        break;
                                                    }
                                                }
                                            }
                                        }
                                        plot.owner = buyer;
                                        plot.customprice = 0.0;
                                        plot.forsale = false;
                                        plot.updateField("owner", buyer);
                                        plot.updateField("customprice", 0);
                                        plot.updateField("forsale", false);
                                        PlotManager.adjustWall(l);
                                        PlotManager.setSellSign(w, plot);
                                        PlotManager.setOwnerSign(w, plot);
                                        this.Send(p, this.C("MsgPlotBought") + " " + this.f(-cost));
                                        if (this.isAdv) {
                                            PlotMe.logger.info(this.LOG + buyer + " " + this.C("MsgBoughtPlot") + " " + id + " " + this.C("WordFor") + " " + cost);
                                        }
                                    }
                                    else {
                                        this.Send(p, this.RED + er.errorMessage);
                                        this.warn(er.errorMessage);
                                    }
                                }
                            }
                        }
                    }
                }
                else {
                    this.Send(p, this.RED + this.C("MsgThisPlot") + "(" + id + ") " + this.C("MsgHasNoOwner"));
                }
            }
            else {
                this.Send(p, this.RED + this.C("MsgPermissionDenied"));
            }
        }
        else {
            this.Send(p, this.RED + this.C("MsgEconomyDisabledWorld"));
        }
        return true;
    }
    
    private boolean auction(final Player p, final String[] args) {
        if (PlotManager.isEconomyEnabled(p)) {
            final PlotMapInfo pmi = PlotManager.getMap(p);
            if (pmi.CanPutOnSale) {
                if (PlotMe.cPerms(p, "PlotMe.use.auction") || PlotMe.cPerms(p, "PlotMe.admin.auction")) {
                    final String id = PlotManager.getPlotId(p.getLocation());
                    if (id.equals("")) {
                        this.Send(p, this.RED + this.C("MsgNoPlotFound"));
                    }
                    else if (!PlotManager.isPlotAvailable(id, p)) {
                        final Plot plot = PlotManager.getPlotById(p, id);
                        final String name = p.getName();
                        if (plot.owner.equalsIgnoreCase(name) || PlotMe.cPerms(p, "PlotMe.admin.auction")) {
                            final World w = p.getWorld();
                            if (plot.auctionned) {
                                if (plot.currentbidder != null) {
                                    if (!plot.currentbidder.equalsIgnoreCase("")) {
                                        if (!PlotMe.cPerms(p, "PlotMe.admin.auction")) {
                                            this.Send(p, this.RED + this.C("MsgPlotHasBidsAskAdmin"));
                                        }
                                        else {
                                            if (plot.currentbidder != null) {
                                                final OfflinePlayer playercurrentbidder = Bukkit.getOfflinePlayer(plot.currentbidderId);
                                                final EconomyResponse er = PlotMe.economy.depositPlayer(playercurrentbidder, plot.currentbid);
                                                if (!er.transactionSuccess()) {
                                                    this.Send(p, this.RED + er.errorMessage);
                                                    this.warn(er.errorMessage);
                                                }
                                                else {
                                                    for (final Player player : Bukkit.getServer().getOnlinePlayers()) {
                                                        if (plot.currentbidder != null && player.getName().equalsIgnoreCase(plot.currentbidder)) {
                                                            this.Send(player, this.C("MsgAuctionCancelledOnPlot") + " " + id + " " + this.C("MsgOwnedBy") + " " + plot.owner + ". " + this.f(plot.currentbid));
                                                            break;
                                                        }
                                                    }
                                                }
                                            }
                                            plot.auctionned = false;
                                            PlotManager.adjustWall(p.getLocation());
                                            PlotManager.setSellSign(w, plot);
                                            plot.currentbid = 0.0;
                                            plot.currentbidder = "";
                                            plot.updateField("currentbid", 0);
                                            plot.updateField("currentbidder", "");
                                            plot.updateField("auctionned", false);
                                            this.Send(p, this.C("MsgAuctionCancelled"));
                                            if (this.isAdv) {
                                                PlotMe.logger.info(this.LOG + name + " " + this.C("MsgStoppedTheAuctionOnPlot") + " " + id);
                                            }
                                        }
                                    }
                                    else {
                                        plot.auctionned = false;
                                        PlotManager.adjustWall(p.getLocation());
                                        PlotManager.setSellSign(w, plot);
                                        plot.currentbid = 0.0;
                                        plot.currentbidder = "";
                                        plot.updateField("currentbid", 0);
                                        plot.updateField("currentbidder", "");
                                        plot.updateField("auctionned", false);
                                        this.Send(p, this.C("MsgAuctionCancelled"));
                                        if (this.isAdv) {
                                            PlotMe.logger.info(this.LOG + name + " " + this.C("MsgStoppedTheAuctionOnPlot") + " " + id);
                                        }
                                    }
                                }
                                else {
                                    plot.auctionned = false;
                                    PlotManager.adjustWall(p.getLocation());
                                    PlotManager.setSellSign(w, plot);
                                    plot.currentbid = 0.0;
                                    plot.currentbidder = "";
                                    plot.updateField("currentbid", 0);
                                    plot.updateField("currentbidder", "");
                                    plot.updateField("auctionned", false);
                                    this.Send(p, this.C("MsgAuctionCancelled"));
                                    if (this.isAdv) {
                                        PlotMe.logger.info(this.LOG + name + " " + this.C("MsgStoppedTheAuctionOnPlot") + " " + id);
                                    }
                                }
                            }
                            else {
                                double bid = 1.0;
                                if (args.length == 2) {
                                    try {
                                        bid = Double.parseDouble(args[1]);
                                    }
                                    catch (NumberFormatException ex) {}
                                }
                                if (bid < 0.0) {
                                    this.Send(p, this.RED + this.C("MsgInvalidAmount"));
                                }
                                else {
                                    plot.currentbid = bid;
                                    plot.auctionned = true;
                                    PlotManager.adjustWall(p.getLocation());
                                    PlotManager.setSellSign(w, plot);
                                    plot.updateField("currentbid", bid);
                                    plot.updateField("auctionned", true);
                                    this.Send(p, this.C("MsgAuctionStarted"));
                                    if (this.isAdv) {
                                        PlotMe.logger.info(this.LOG + name + " " + this.C("MsgStartedAuctionOnPlot") + " " + id + " " + this.C("WordAt") + " " + bid);
                                    }
                                }
                            }
                        }
                        else {
                            this.Send(p, this.RED + this.C("MsgDoNotOwnPlot"));
                        }
                    }
                    else {
                        this.Send(p, this.RED + this.C("MsgThisPlot") + "(" + id + ") " + this.C("MsgHasNoOwner"));
                    }
                }
                else {
                    this.Send(p, this.RED + this.C("MsgPermissionDenied"));
                }
            }
            else {
                this.Send(p, this.RED + this.C("MsgSellingPlotsIsDisabledWorld"));
            }
        }
        else {
            this.Send(p, this.RED + this.C("MsgEconomyDisabledWorld"));
        }
        return true;
    }
    
    private boolean dispose(final Player p, final String[] args) {
        if (PlotMe.cPerms(p, "PlotMe.admin.dispose") || PlotMe.cPerms(p, "PlotMe.use.dispose")) {
            if (!PlotManager.isPlotWorld(p)) {
                this.Send(p, this.RED + this.C("MsgNotPlotWorld"));
            }
            else {
                final String id = PlotManager.getPlotId(p.getLocation());
                if (id.equals("")) {
                    this.Send(p, this.RED + this.C("MsgNoPlotFound"));
                }
                else if (!PlotManager.isPlotAvailable(id, p)) {
                    final Plot plot = PlotManager.getPlotById(p, id);
                    if (plot.protect) {
                        this.Send(p, this.RED + this.C("MsgPlotProtectedNotDisposed"));
                    }
                    else {
                        final String name = p.getName();
                        if (plot.owner.equalsIgnoreCase(name) || PlotMe.cPerms(p, "PlotMe.admin.dispose")) {
                            final PlotMapInfo pmi = PlotManager.getMap(p);
                            final double cost = pmi.DisposePrice;
                            if (PlotManager.isEconomyEnabled(p)) {
                                if (cost != 0.0 && PlotMe.economy.getBalance(p) < cost) {
                                    this.Send(p, this.RED + this.C("MsgNotEnoughDispose"));
                                    return true;
                                }
                                final EconomyResponse er = PlotMe.economy.withdrawPlayer(p, cost);
                                if (!er.transactionSuccess()) {
                                    this.Send(p, this.RED + er.errorMessage);
                                    this.warn(er.errorMessage);
                                    return true;
                                }
                                if (plot.auctionned) {
                                    final String currentbidder = plot.currentbidder;
                                    final OfflinePlayer playercurrentbidder = Bukkit.getOfflinePlayer(plot.currentbidderId);
                                    if (!currentbidder.equals("")) {
                                        final EconomyResponse er2 = PlotMe.economy.depositPlayer(playercurrentbidder, plot.currentbid);
                                        if (!er2.transactionSuccess()) {
                                            this.Send(p, this.RED + er2.errorMessage);
                                            this.warn(er2.errorMessage);
                                        }
                                        else {
                                            for (final Player player : Bukkit.getServer().getOnlinePlayers()) {
                                                if (player.getName().equalsIgnoreCase(currentbidder)) {
                                                    this.Send(player, this.C("WordPlot") + " " + id + " " + this.C("MsgOwnedBy") + " " + plot.owner + " " + this.C("MsgWasDisposed") + " " + this.f(cost));
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            final World w = p.getWorld();
                            if (!PlotManager.isPlotAvailable(id, p)) {
                                PlotManager.getPlots(p).remove(id);
                            }
                            PlotManager.removeOwnerSign(w, id);
                            PlotManager.removeSellSign(w, id);
                            SqlManager.deletePlot(PlotManager.getIdX(id), PlotManager.getIdZ(id), w.getName().toLowerCase());
                            this.Send(p, this.C("MsgPlotDisposedAnyoneClaim"));
                            if (this.isAdv) {
                                PlotMe.logger.info(this.LOG + name + " " + this.C("MsgDisposedPlot") + " " + id);
                            }
                        }
                        else {
                            this.Send(p, this.RED + this.C("MsgThisPlot") + "(" + id + ") " + this.C("MsgNotYoursCannotDispose"));
                        }
                    }
                }
                else {
                    this.Send(p, this.RED + this.C("MsgThisPlot") + "(" + id + ") " + this.C("MsgHasNoOwner"));
                }
            }
        }
        else {
            this.Send(p, this.RED + this.C("MsgPermissionDenied"));
        }
        return true;
    }
    
    private boolean sell(final Player p, final String[] args) {
        if (PlotManager.isEconomyEnabled(p)) {
            final PlotMapInfo pmi = PlotManager.getMap(p);
            if (pmi.CanSellToBank || pmi.CanPutOnSale) {
                if (PlotMe.cPerms(p, "PlotMe.use.sell") || PlotMe.cPerms(p, "PlotMe.admin.sell")) {
                    final Location l = p.getLocation();
                    final String id = PlotManager.getPlotId(l);
                    if (id.equals("")) {
                        this.Send(p, this.RED + this.C("MsgNoPlotFound"));
                    }
                    else if (!PlotManager.isPlotAvailable(id, p)) {
                        final Plot plot = PlotManager.getPlotById(p, id);
                        if (plot.owner.equalsIgnoreCase(p.getName()) || PlotMe.cPerms(p, "PlotMe.admin.sell")) {
                            final World w = p.getWorld();
                            final String name = p.getName();
                            if (plot.forsale) {
                                plot.customprice = 0.0;
                                plot.forsale = false;
                                plot.updateField("customprice", 0);
                                plot.updateField("forsale", false);
                                PlotManager.adjustWall(l);
                                PlotManager.setSellSign(w, plot);
                                this.Send(p, this.C("MsgPlotNoLongerSale"));
                                if (this.isAdv) {
                                    PlotMe.logger.info(this.LOG + name + " " + this.C("MsgRemovedPlot") + " " + id + " " + this.C("MsgFromBeingSold"));
                                }
                            }
                            else {
                                double price = pmi.SellToPlayerPrice;
                                boolean bank = false;
                                if (args.length == 2) {
                                    if (args[1].equalsIgnoreCase("bank")) {
                                        bank = true;
                                    }
                                    else {
                                        if (!pmi.CanCustomizeSellPrice) {
                                            this.Send(p, this.RED + this.C("MsgCannotCustomPriceDefault") + " " + price);
                                            return true;
                                        }
                                        try {
                                            price = Double.parseDouble(args[1]);
                                        }
                                        catch (Exception e) {
                                            if (pmi.CanSellToBank) {
                                                this.Send(p, this.C("WordUsage") + ": " + this.RED + " /plotme " + this.C("CommandSellBank") + "|<" + this.C("WordAmount") + ">");
                                                p.sendMessage("  " + this.C("WordExample") + ": " + this.RED + "/plotme " + this.C("CommandSellBank") + " " + this.RESET + " or " + this.RED + " /plotme " + this.C("CommandSell") + " 200");
                                            }
                                            else {
                                                this.Send(p, this.C("WordUsage") + ": " + this.RED + " /plotme " + this.C("CommandSell") + " <" + this.C("WordAmount") + ">" + this.RESET + " " + this.C("WordExample") + ": " + this.RED + "/plotme " + this.C("CommandSell") + " 200");
                                            }
                                        }
                                    }
                                }
                                if (bank) {
                                    if (!pmi.CanSellToBank) {
                                        this.Send(p, this.RED + this.C("MsgCannotSellToBank"));
                                    }
                                    else {
                                        final String currentbidder = plot.currentbidder;
                                        if (!currentbidder.equals("")) {
                                            final double bid = plot.currentbid;
                                            final OfflinePlayer playercurrentbidder = Bukkit.getOfflinePlayer(plot.currentbidderId);
                                            final EconomyResponse er = PlotMe.economy.depositPlayer(playercurrentbidder, bid);
                                            if (!er.transactionSuccess()) {
                                                this.Send(p, this.RED + er.errorMessage);
                                                this.warn(er.errorMessage);
                                            }
                                            else {
                                                for (final Player player : Bukkit.getServer().getOnlinePlayers()) {
                                                    if (player.getName().equalsIgnoreCase(currentbidder)) {
                                                        this.Send(player, this.C("WordPlot") + " " + id + " " + this.C("MsgOwnedBy") + " " + plot.owner + " " + this.C("MsgSoldToBank") + " " + this.f(bid));
                                                        break;
                                                    }
                                                }
                                            }
                                        }
                                        final double sellprice = pmi.SellToBankPrice;
                                        final EconomyResponse er2 = PlotMe.economy.depositPlayer(p, sellprice);
                                        if (er2.transactionSuccess()) {
                                            plot.owner = "$Bank$";
                                            plot.forsale = true;
                                            plot.customprice = pmi.BuyFromBankPrice;
                                            plot.auctionned = false;
                                            plot.currentbidder = "";
                                            plot.currentbid = 0.0;
                                            plot.removeAllAllowed();
                                            PlotManager.setOwnerSign(w, plot);
                                            PlotManager.setSellSign(w, plot);
                                            plot.updateField("owner", plot.owner);
                                            plot.updateField("forsale", true);
                                            plot.updateField("auctionned", true);
                                            plot.updateField("customprice", plot.customprice);
                                            plot.updateField("currentbidder", "");
                                            plot.updateField("currentbid", 0);
                                            this.Send(p, this.C("MsgPlotSold") + " " + this.f(sellprice));
                                            if (this.isAdv) {
                                                PlotMe.logger.info(this.LOG + name + " " + this.C("MsgSoldToBankPlot") + " " + id + " " + this.C("WordFor") + " " + sellprice);
                                            }
                                        }
                                        else {
                                            this.Send(p, " " + er2.errorMessage);
                                            this.warn(er2.errorMessage);
                                        }
                                    }
                                }
                                else if (price < 0.0) {
                                    this.Send(p, this.RED + this.C("MsgInvalidAmount"));
                                }
                                else {
                                    plot.customprice = price;
                                    plot.forsale = true;
                                    plot.updateField("customprice", price);
                                    plot.updateField("forsale", true);
                                    PlotManager.adjustWall(l);
                                    PlotManager.setSellSign(w, plot);
                                    this.Send(p, this.C("MsgPlotForSale"));
                                    if (this.isAdv) {
                                        PlotMe.logger.info(this.LOG + name + " " + this.C("MsgPutOnSalePlot") + " " + id + " " + this.C("WordFor") + " " + price);
                                    }
                                }
                            }
                        }
                        else {
                            this.Send(p, this.RED + this.C("MsgDoNotOwnPlot"));
                        }
                    }
                    else {
                        this.Send(p, this.RED + this.C("MsgThisPlot") + "(" + id + ") " + this.C("MsgHasNoOwner"));
                    }
                }
                else {
                    this.Send(p, this.RED + this.C("MsgPermissionDenied"));
                }
            }
            else {
                this.Send(p, this.RED + this.C("MsgSellingPlotsIsDisabledWorld"));
            }
        }
        else {
            this.Send(p, this.RED + this.C("MsgEconomyDisabledWorld"));
        }
        return true;
    }
    
    private boolean protect(final Player p, final String[] args) {
        if (PlotMe.cPerms(p, "PlotMe.admin.protect") || PlotMe.cPerms(p, "PlotMe.use.protect")) {
            if (!PlotManager.isPlotWorld(p)) {
                this.Send(p, this.RED + this.C("MsgNotPlotWorld"));
                return true;
            }
            final String id = PlotManager.getPlotId(p.getLocation());
            if (id.equals("")) {
                this.Send(p, this.RED + this.C("MsgNoPlotFound"));
            }
            else if (!PlotManager.isPlotAvailable(id, p)) {
                final Plot plot = PlotManager.getPlotById(p, id);
                final String name = p.getName();
                if (plot.owner.equalsIgnoreCase(name) || PlotMe.cPerms(p, "PlotMe.admin.protect")) {
                    if (plot.protect) {
                        plot.protect = false;
                        PlotManager.adjustWall(p.getLocation());
                        plot.updateField("protected", false);
                        this.Send(p, this.C("MsgPlotNoLongerProtected"));
                        if (this.isAdv) {
                            PlotMe.logger.info(this.LOG + name + " " + this.C("MsgUnprotectedPlot") + " " + id);
                        }
                    }
                    else {
                        final PlotMapInfo pmi = PlotManager.getMap(p);
                        double cost = 0.0;
                        if (PlotManager.isEconomyEnabled(p)) {
                            cost = pmi.ProtectPrice;
                            if (PlotMe.economy.getBalance(p) < cost) {
                                this.Send(p, this.RED + this.C("MsgNotEnoughProtectPlot"));
                                return true;
                            }
                            final EconomyResponse er = PlotMe.economy.withdrawPlayer(p, cost);
                            if (!er.transactionSuccess()) {
                                this.Send(p, this.RED + er.errorMessage);
                                this.warn(er.errorMessage);
                                return true;
                            }
                        }
                        plot.protect = true;
                        PlotManager.adjustWall(p.getLocation());
                        plot.updateField("protected", true);
                        this.Send(p, this.C("MsgPlotNowProtected") + " " + this.f(-cost));
                        if (this.isAdv) {
                            PlotMe.logger.info(this.LOG + name + " " + this.C("MsgProtectedPlot") + " " + id);
                        }
                    }
                }
                else {
                    this.Send(p, this.RED + this.C("MsgDoNotOwnPlot"));
                }
            }
            else {
                this.Send(p, this.RED + this.C("MsgThisPlot") + "(" + id + ") " + this.C("MsgHasNoOwner"));
            }
        }
        else {
            this.Send(p, this.RED + this.C("MsgPermissionDenied"));
        }
        return true;
    }
    
    private boolean donelist(final Player p, final String[] args) {
        if (PlotMe.cPerms(p, "PlotMe.admin.done")) {
            final PlotMapInfo pmi = PlotManager.getMap(p);
            if (pmi == null) {
                this.Send(p, this.RED + this.C("MsgNotPlotWorld"));
                return true;
            }
            final HashMap<String, Plot> plots = pmi.plots;
            final List<Plot> finishedplots = new ArrayList<>();
            int nbfinished = 0;
            final int pagesize = 8;
            int page = 1;
            if (args.length == 2) {
                try {
                    page = Integer.parseInt(args[1]);
                }
                catch (NumberFormatException ex) {}
            }
            for (final String id : plots.keySet()) {
                final Plot plot = plots.get(id);
                if (plot.finished) {
                    finishedplots.add(plot);
                    ++nbfinished;
                }
            }
            Collections.sort(finishedplots, new PlotFinishedComparator());
            final int maxpage = (int)Math.ceil(nbfinished / (double)pagesize);
            if (finishedplots.size() == 0) {
                this.Send(p, this.C("MsgNoPlotsFinished"));
            }
            else {
                this.Send(p, this.C("MsgFinishedPlotsPage") + " " + page + "/" + maxpage);
                for (int i = (page - 1) * pagesize; i < finishedplots.size() && i < page * pagesize; ++i) {
                    final Plot plot2 = finishedplots.get(i);
                    final String starttext = "  " + this.BLUE + plot2.id + this.RESET + " -> " + plot2.owner;
                    final int textLength = MinecraftFontWidthCalculator.getStringWidth(starttext);
                    final String line = starttext + this.whitespace(550 - textLength) + "@" + plot2.finisheddate;
                    p.sendMessage(line);
                }
            }
        }
        else {
            this.Send(p, this.RED + this.C("MsgPermissionDenied"));
        }
        return true;
    }
    
    private boolean done(final Player p, final String[] args) {
        if (PlotMe.cPerms(p, "PlotMe.use.done") || PlotMe.cPerms(p, "PlotMe.admin.done")) {
            if (!PlotManager.isPlotWorld(p)) {
                this.Send(p, this.RED + this.C("MsgNotPlotWorld"));
                return true;
            }
            final String id = PlotManager.getPlotId(p.getLocation());
            if (id.equals("")) {
                this.Send(p, this.RED + this.C("MsgNoPlotFound"));
            }
            else if (!PlotManager.isPlotAvailable(id, p)) {
                final Plot plot = PlotManager.getPlotById(p, id);
                final String name = p.getName();
                if (plot.owner.equalsIgnoreCase(name) || PlotMe.cPerms(p, "PlotMe.admin.done")) {
                    if (plot.finished) {
                        plot.setUnfinished();
                        this.Send(p, this.C("MsgUnmarkFinished"));
                        if (this.isAdv) {
                            PlotMe.logger.info(this.LOG + name + " " + this.C("WordMarked") + " " + id + " " + this.C("WordFinished"));
                        }
                    }
                    else {
                        plot.setFinished();
                        this.Send(p, this.C("MsgMarkFinished"));
                        if (this.isAdv) {
                            PlotMe.logger.info(this.LOG + name + " " + this.C("WordMarked") + " " + id + " " + this.C("WordUnfinished"));
                        }
                    }
                }
            }
            else {
                this.Send(p, this.RED + this.C("MsgThisPlot") + "(" + id + ") " + this.C("MsgHasNoOwner"));
            }
        }
        else {
            this.Send(p, this.RED + this.C("MsgPermissionDenied"));
        }
        return true;
    }
    
    private boolean addtime(final Player p, final String[] args) {
        if (PlotMe.cPerms(p, "PlotMe.admin.addtime")) {
            if (!PlotManager.isPlotWorld(p)) {
                this.Send(p, this.RED + this.C("MsgNotPlotWorld"));
                return true;
            }
            final String id = PlotManager.getPlotId(p.getLocation());
            if (id.equals("")) {
                this.Send(p, this.RED + this.C("MsgNoPlotFound"));
            }
            else if (!PlotManager.isPlotAvailable(id, p)) {
                final Plot plot = PlotManager.getPlotById(p, id);
                if (plot != null) {
                    final String name = p.getName();
                    plot.resetExpire(PlotManager.getMap(p).DaysToExpiration);
                    this.Send(p, this.C("MsgPlotExpirationReset"));
                    if (this.isAdv) {
                        PlotMe.logger.info(this.LOG + name + " reset expiration on plot " + id);
                    }
                }
            }
            else {
                this.Send(p, this.RED + this.C("MsgThisPlot") + "(" + id + ") " + this.C("MsgHasNoOwner"));
            }
        }
        else {
            this.Send(p, this.RED + this.C("MsgPermissionDenied"));
        }
        return true;
    }
    
    private boolean expired(final Player p, final String[] args) {
        if (PlotMe.cPerms(p, "PlotMe.admin.expired")) {
            if (!PlotManager.isPlotWorld(p)) {
                this.Send(p, this.RED + this.C("MsgNotPlotWorld"));
                return true;
            }
            final int pagesize = 8;
            int page = 1;
            int nbexpiredplots = 0;
            final World w = p.getWorld();
            final List<Plot> expiredplots = new ArrayList<>();
            final HashMap<String, Plot> plots = PlotManager.getPlots(w);
            final String date = PlotMe.getDate();
            if (args.length == 2) {
                try {
                    page = Integer.parseInt(args[1]);
                }
                catch (NumberFormatException ex) {}
            }
            for (final String id : plots.keySet()) {
                final Plot plot = plots.get(id);
                if (!plot.protect && plot.expireddate != null && PlotMe.getDate(plot.expireddate).compareTo(date) < 0) {
                    ++nbexpiredplots;
                    expiredplots.add(plot);
                }
            }
            Collections.sort(expiredplots);
            final int maxpage = (int)Math.ceil(nbexpiredplots / (double)pagesize);
            if (expiredplots.size() == 0) {
                this.Send(p, this.C("MsgNoPlotExpired"));
            }
            else {
                this.Send(p, this.C("MsgExpiredPlotsPage") + " " + page + "/" + maxpage);
                for (int i = (page - 1) * pagesize; i < expiredplots.size() && i < page * pagesize; ++i) {
                    final Plot plot2 = expiredplots.get(i);
                    final String starttext = "  " + this.BLUE + plot2.id + this.RESET + " -> " + plot2.owner;
                    final int textLength = MinecraftFontWidthCalculator.getStringWidth(starttext);
                    final String line = starttext + this.whitespace(550 - textLength) + "@" + plot2.expireddate.toString();
                    p.sendMessage(line);
                }
            }
        }
        else {
            this.Send(p, this.RED + this.C("MsgPermissionDenied"));
        }
        return true;
    }
    
    private boolean plotlist(final Player p, final String[] args) {
        if (PlotMe.cPerms(p, "PlotMe.use.list")) {
            if (!PlotManager.isPlotWorld(p)) {
                this.Send(p, this.RED + this.C("MsgNotPlotWorld"));
                return true;
            }
            final String pname = p.getName();
            String name;
            if (PlotMe.cPerms(p, "PlotMe.admin.list") && args.length == 2) {
                name = args[1];
                this.Send(p, this.C("MsgListOfPlotsWhere") + " " + this.BLUE + name + this.RESET + " " + this.C("MsgCanBuild"));
            }
            else {
                name = p.getName();
                this.Send(p, this.C("MsgListOfPlotsWhereYou"));
            }
            for (final Plot plot : PlotManager.getPlots(p).values()) {
                final StringBuilder addition = new StringBuilder();
                if (plot.expireddate != null) {
                    final Date tempdate = plot.expireddate;
                    if (tempdate.compareTo(Calendar.getInstance().getTime()) < 0) {
                        addition.append(this.RED + " @" + plot.expireddate.toString() + this.RESET);
                    }
                    else {
                        addition.append(" @").append(plot.expireddate.toString());
                    }
                }
                if (plot.auctionned) {
                    addition.append(" ").append(this.C("WordAuction")).append(": ").append(this.GREEN).append(this.round(plot.currentbid)).append(this.RESET).append((plot.currentbidder != null && !plot.currentbidder.equals("")) ? (" " + plot.currentbidder) : "");
                }
                if (plot.forsale) {
                    addition.append(" ").append(this.C("WordSell")).append(": ").append(this.GREEN).append(this.round(plot.customprice)).append(this.RESET);
                }
                if (plot.owner.equalsIgnoreCase(name)) {
                    if (plot.allowedcount() == 0) {
                        if (name.equalsIgnoreCase(pname)) {
                            p.sendMessage("  " + plot.id + " -> " + this.BLUE + this.ITALIC + this.C("WordYours") + this.RESET + addition);
                        }
                        else {
                            p.sendMessage("  " + plot.id + " -> " + this.BLUE + this.ITALIC + plot.owner + this.RESET + addition);
                        }
                    }
                    else {
                        final StringBuilder helpers = new StringBuilder();
                        for (int i = 0; i < plot.allowedcount(); ++i) {
                            helpers.append(this.BLUE).append(plot.allowed().toArray()[i]).append(this.RESET).append(", ");
                        }
                        if (helpers.length() > 2) {
                            helpers.delete(helpers.length() - 2, helpers.length());
                        }
                        if (name.equalsIgnoreCase(pname)) {
                            p.sendMessage("  " + plot.id + " -> " + this.BLUE + this.ITALIC + this.C("WordYours") + this.RESET + addition + ", " + this.C("WordHelpers") + ": " + helpers);
                        }
                        else {
                            p.sendMessage("  " + plot.id + " -> " + this.BLUE + this.ITALIC + plot.owner + this.RESET + addition + ", " + this.C("WordHelpers") + ": " + helpers);
                        }
                    }
                }
                else {
                    if (!plot.isAllowedConsulting(name)) {
                        continue;
                    }
                    final StringBuilder helpers = new StringBuilder();
                    for (int i = 0; i < plot.allowedcount(); ++i) {
                        if (p.getName().equalsIgnoreCase((String)plot.allowed().toArray()[i])) {
                            if (name.equalsIgnoreCase(pname)) {
                                helpers.append(this.BLUE).append(this.ITALIC).append("You").append(this.RESET).append(", ");
                            }
                            else {
                                helpers.append(this.BLUE).append(this.ITALIC).append(args[1]).append(this.RESET).append(", ");
                            }
                        }
                        else {
                            helpers.append(this.BLUE).append(plot.allowed().toArray()[i]).append(this.RESET).append(", ");
                        }
                    }
                    if (helpers.length() > 2) {
                        helpers.delete(helpers.length() - 2, helpers.length());
                    }
                    if (plot.owner.equalsIgnoreCase(pname)) {
                        p.sendMessage("  " + plot.id + " -> " + this.BLUE + this.C("WordYours") + this.RESET + addition + ", " + this.C("WordHelpers") + ": " + helpers);
                    }
                    else {
                        p.sendMessage("  " + plot.id + " -> " + this.BLUE + plot.owner + this.C("WordPossessive") + this.RESET + addition + ", " + this.C("WordHelpers") + ": " + helpers);
                    }
                }
            }
        }
        else {
            this.Send(p, this.RED + this.C("MsgPermissionDenied"));
        }
        return true;
    }
    
    private boolean weanywhere(final Player p, final String[] args) {
        if (PlotMe.cPerms(p, "PlotMe.admin.weanywhere")) {
            final String name = p.getName();
            if ((PlotMe.isIgnoringWELimit(p) && !PlotMe.defaultWEAnywhere) || (!PlotMe.isIgnoringWELimit(p) && PlotMe.defaultWEAnywhere)) {
                PlotMe.removeIgnoreWELimit(p);
            }
            else {
                PlotMe.addIgnoreWELimit(p);
            }
            if (PlotMe.isIgnoringWELimit(p)) {
                this.Send(p, this.C("MsgWorldEditAnywhere"));
                if (this.isAdv) {
                    PlotMe.logger.info(this.LOG + name + " enabled WorldEdit anywhere");
                }
            }
            else {
                this.Send(p, this.C("MsgWorldEditInYourPlots"));
                if (this.isAdv) {
                    PlotMe.logger.info(this.LOG + name + " disabled WorldEdit anywhere");
                }
            }
        }
        else {
            this.Send(p, this.RED + this.C("MsgPermissionDenied"));
        }
        return true;
    }
    
    private boolean showhelp(final Player p, int page) {
        final int max = 4;
        int maxpage = 0;
        final boolean ecoon = PlotManager.isEconomyEnabled(p);
        final List<String> allowed_commands = new ArrayList<>();
        allowed_commands.add("limit");
        if (PlotMe.cPerms(p, "PlotMe.use.claim")) {
            allowed_commands.add("claim");
        }
        if (PlotMe.cPerms(p, "PlotMe.use.claim.other")) {
            allowed_commands.add("claim.other");
        }
        if (PlotMe.cPerms(p, "PlotMe.use.auto")) {
            allowed_commands.add("auto");
        }
        if (PlotMe.cPerms(p, "PlotMe.use.home")) {
            allowed_commands.add("home");
        }
        if (PlotMe.cPerms(p, "PlotMe.use.home.other")) {
            allowed_commands.add("home.other");
        }
        if (PlotMe.cPerms(p, "PlotMe.use.info")) {
            allowed_commands.add("info");
            allowed_commands.add("biomeinfo");
        }
        if (PlotMe.cPerms(p, "PlotMe.use.comment")) {
            allowed_commands.add("comment");
        }
        if (PlotMe.cPerms(p, "PlotMe.use.comments")) {
            allowed_commands.add("comments");
        }
        if (PlotMe.cPerms(p, "PlotMe.use.list")) {
            allowed_commands.add("list");
        }
        if (PlotMe.cPerms(p, "PlotMe.use.biome")) {
            allowed_commands.add("biome");
            allowed_commands.add("biomelist");
        }
        if (PlotMe.cPerms(p, "PlotMe.use.done") || PlotMe.cPerms(p, "PlotMe.admin.done")) {
            allowed_commands.add("done");
        }
        if (PlotMe.cPerms(p, "PlotMe.admin.done")) {
            allowed_commands.add("donelist");
        }
        if (PlotMe.cPerms(p, "PlotMe.admin.tp")) {
            allowed_commands.add("tp");
        }
        if (PlotMe.cPerms(p, "PlotMe.admin.id")) {
            allowed_commands.add("id");
        }
        if (PlotMe.cPerms(p, "PlotMe.use.clear") || PlotMe.cPerms(p, "PlotMe.admin.clear")) {
            allowed_commands.add("clear");
        }
        if (PlotMe.cPerms(p, "PlotMe.admin.dispose") || PlotMe.cPerms(p, "PlotMe.use.dispose")) {
            allowed_commands.add("dispose");
        }
        if (PlotMe.cPerms(p, "PlotMe.admin.reset")) {
            allowed_commands.add("reset");
        }
        if (PlotMe.cPerms(p, "PlotMe.use.add") || PlotMe.cPerms(p, "PlotMe.admin.add")) {
            allowed_commands.add("add");
        }
        if (PlotMe.cPerms(p, "PlotMe.use.remove") || PlotMe.cPerms(p, "PlotMe.admin.remove")) {
            allowed_commands.add("remove");
        }
        if (PlotMe.allowToDeny) {
            if (PlotMe.cPerms(p, "PlotMe.use.deny") || PlotMe.cPerms(p, "PlotMe.admin.deny")) {
                allowed_commands.add("deny");
            }
            if (PlotMe.cPerms(p, "PlotMe.use.undeny") || PlotMe.cPerms(p, "PlotMe.admin.undeny")) {
                allowed_commands.add("undeny");
            }
        }
        if (PlotMe.cPerms(p, "PlotMe.admin.setowner")) {
            allowed_commands.add("setowner");
        }
        if (PlotMe.cPerms(p, "PlotMe.admin.move")) {
            allowed_commands.add("move");
        }
        if (PlotMe.cPerms(p, "PlotMe.admin.weanywhere")) {
            allowed_commands.add("weanywhere");
        }
        if (PlotMe.cPerms(p, "PlotMe.admin.reload")) {
            allowed_commands.add("reload");
        }
        if (PlotMe.cPerms(p, "PlotMe.admin.list")) {
            allowed_commands.add("listother");
        }
        if (PlotMe.cPerms(p, "PlotMe.admin.expired")) {
            allowed_commands.add("expired");
        }
        if (PlotMe.cPerms(p, "PlotMe.admin.addtime")) {
            allowed_commands.add("addtime");
        }
        if (PlotMe.cPerms(p, "PlotMe.admin.resetexpired")) {
            allowed_commands.add("resetexpired");
        }
        final PlotMapInfo pmi = PlotManager.getMap(p);
        if (PlotManager.isPlotWorld(p) && ecoon) {
            if (PlotMe.cPerms(p, "PlotMe.use.buy")) {
                allowed_commands.add("buy");
            }
            if (PlotMe.cPerms(p, "PlotMe.use.sell")) {
                allowed_commands.add("sell");
                if (pmi.CanSellToBank) {
                    allowed_commands.add("sellbank");
                }
            }
            if (PlotMe.cPerms(p, "PlotMe.use.auction")) {
                allowed_commands.add("auction");
            }
            if (PlotMe.cPerms(p, "PlotMe.use.bid")) {
                allowed_commands.add("bid");
            }
        }
        maxpage = (int)Math.ceil(allowed_commands.size() / (double)max);
        if (page > maxpage) {
            page = 1;
        }
        p.sendMessage(this.RED + " ---==" + this.BLUE + this.C("HelpTitle") + " " + page + "/" + maxpage + this.RED + "==--- ");
        for (int ctr = (page - 1) * max; ctr < page * max && ctr < allowed_commands.size(); ++ctr) {
            final String allowedcmd = allowed_commands.get(ctr);
            if (allowedcmd.equalsIgnoreCase("limit")) {
                if (PlotManager.isPlotWorld(p) || PlotMe.allowWorldTeleport) {
                    World w = null;
                    if (PlotManager.isPlotWorld(p)) {
                        w = p.getWorld();
                    }
                    else if (PlotMe.allowWorldTeleport) {
                        w = PlotManager.getFirstWorld();
                    }
                    final int maxplots = PlotMe.getPlotLimit(p);
                    final int ownedplots = PlotManager.getNbOwnedPlot(p, w);
                    if (maxplots == -1) {
                        p.sendMessage(this.GREEN + this.C("HelpYourPlotLimitWorld") + " : " + this.AQUA + ownedplots + this.GREEN + " " + this.C("HelpUsedOf") + " " + this.AQUA + this.C("WordInfinite"));
                    }
                    else {
                        p.sendMessage(this.GREEN + this.C("HelpYourPlotLimitWorld") + " : " + this.AQUA + ownedplots + this.GREEN + " " + this.C("HelpUsedOf") + " " + this.AQUA + maxplots);
                    }
                }
                else {
                    p.sendMessage(this.GREEN + this.C("HelpYourPlotLimitWorld") + " : " + this.AQUA + this.C("MsgNotPlotWorld"));
                }
            }
            else if (allowedcmd.equalsIgnoreCase("claim")) {
                p.sendMessage(this.GREEN + " /plotme " + this.C("CommandClaim"));
                if (ecoon && pmi != null && pmi.ClaimPrice != 0.0) {
                    p.sendMessage(this.AQUA + " " + this.C("HelpClaim") + " " + this.C("WordPrice") + " : " + this.RESET + this.round(pmi.ClaimPrice));
                }
                else {
                    p.sendMessage(this.AQUA + " " + this.C("HelpClaim"));
                }
            }
            else if (allowedcmd.equalsIgnoreCase("claim.other")) {
                p.sendMessage(this.GREEN + " /plotme " + this.C("CommandClaim") + " <" + this.C("WordPlayer") + ">");
                if (ecoon && pmi != null && pmi.ClaimPrice != 0.0) {
                    p.sendMessage(this.AQUA + " " + this.C("HelpClaimOther") + " " + this.C("WordPrice") + " : " + this.RESET + this.round(pmi.ClaimPrice));
                }
                else {
                    p.sendMessage(this.AQUA + " " + this.C("HelpClaimOther"));
                }
            }
            else if (allowedcmd.equalsIgnoreCase("auto")) {
                if (PlotMe.allowWorldTeleport) {
                    p.sendMessage(this.GREEN + " /plotme " + this.C("CommandAuto") + " [" + this.C("WordWorld") + "]");
                }
                else {
                    p.sendMessage(this.GREEN + " /plotme " + this.C("CommandAuto"));
                }
                if (ecoon && pmi != null && pmi.ClaimPrice != 0.0) {
                    p.sendMessage(this.AQUA + " " + this.C("HelpAuto") + " " + this.C("WordPrice") + " : " + this.RESET + this.round(pmi.ClaimPrice));
                }
                else {
                    p.sendMessage(this.AQUA + " " + this.C("HelpAuto"));
                }
            }
            else if (allowedcmd.equalsIgnoreCase("home")) {
                if (PlotMe.allowWorldTeleport) {
                    p.sendMessage(this.GREEN + " /plotme " + this.C("CommandHome") + "[:#] [" + this.C("WordWorld") + "]");
                }
                else {
                    p.sendMessage(this.GREEN + " /plotme " + this.C("CommandHome") + "[:#]");
                }
                if (ecoon && pmi != null && pmi.PlotHomePrice != 0.0) {
                    p.sendMessage(this.AQUA + " " + this.C("HelpHome") + " " + this.C("WordPrice") + " : " + this.RESET + this.round(pmi.PlotHomePrice));
                }
                else {
                    p.sendMessage(this.AQUA + " " + this.C("HelpHome"));
                }
            }
            else if (allowedcmd.equalsIgnoreCase("home.other")) {
                if (PlotMe.allowWorldTeleport) {
                    p.sendMessage(this.GREEN + " /plotme " + this.C("CommandHome") + "[:#] <" + this.C("WordPlayer") + "> [" + this.C("WordWorld") + "]");
                }
                else {
                    p.sendMessage(this.GREEN + " /plotme " + this.C("CommandHome") + "[:#] <" + this.C("WordPlayer") + ">");
                }
                if (ecoon && pmi != null && pmi.PlotHomePrice != 0.0) {
                    p.sendMessage(this.AQUA + " " + this.C("HelpHomeOther") + " " + this.C("WordPrice") + " : " + this.RESET + this.round(pmi.PlotHomePrice));
                }
                else {
                    p.sendMessage(this.AQUA + " " + this.C("HelpHomeOther"));
                }
            }
            else if (allowedcmd.equalsIgnoreCase("info")) {
                p.sendMessage(this.GREEN + " /plotme " + this.C("CommandInfo"));
                p.sendMessage(this.AQUA + " " + this.C("HelpInfo"));
            }
            else if (allowedcmd.equalsIgnoreCase("comment")) {
                p.sendMessage(this.GREEN + " /plotme " + this.C("CommandComment") + " <" + this.C("WordComment") + ">");
                if (ecoon && pmi != null && pmi.AddCommentPrice != 0.0) {
                    p.sendMessage(this.AQUA + " " + this.C("HelpComment") + " " + this.C("WordPrice") + " : " + this.RESET + this.round(pmi.AddCommentPrice));
                }
                else {
                    p.sendMessage(this.AQUA + " " + this.C("HelpComment"));
                }
            }
            else if (allowedcmd.equalsIgnoreCase("comments")) {
                p.sendMessage(this.GREEN + " /plotme " + this.C("CommandComments"));
                p.sendMessage(this.AQUA + " " + this.C("HelpComments"));
            }
            else if (allowedcmd.equalsIgnoreCase("list")) {
                p.sendMessage(this.GREEN + " /plotme " + this.C("CommandList"));
                p.sendMessage(this.AQUA + " " + this.C("HelpList"));
            }
            else if (allowedcmd.equalsIgnoreCase("listother")) {
                p.sendMessage(this.GREEN + " /plotme " + this.C("CommandList") + " <" + this.C("WordPlayer") + ">");
                p.sendMessage(this.AQUA + " " + this.C("HelpListOther"));
            }
            else if (allowedcmd.equalsIgnoreCase("biomeinfo")) {
                p.sendMessage(this.GREEN + " /plotme " + this.C("CommandBiome"));
                p.sendMessage(this.AQUA + " " + this.C("HelpBiomeInfo"));
            }
            else if (allowedcmd.equalsIgnoreCase("biome")) {
                p.sendMessage(this.GREEN + " /plotme " + this.C("CommandBiome") + " <" + this.C("WordBiome") + ">");
                if (ecoon && pmi != null && pmi.BiomeChangePrice != 0.0) {
                    p.sendMessage(this.AQUA + " " + this.C("HelpBiome") + " " + this.C("WordPrice") + " : " + this.RESET + this.round(pmi.BiomeChangePrice));
                }
                else {
                    p.sendMessage(this.AQUA + " " + this.C("HelpBiome"));
                }
            }
            else if (allowedcmd.equalsIgnoreCase("biomelist")) {
                p.sendMessage(this.GREEN + " /plotme " + this.C("CommandBiomelist"));
                p.sendMessage(this.AQUA + " " + this.C("HelpBiomeList"));
            }
            else if (allowedcmd.equalsIgnoreCase("done")) {
                p.sendMessage(this.GREEN + " /plotme " + this.C("CommandDone"));
                p.sendMessage(this.AQUA + " " + this.C("HelpDone"));
            }
            else if (allowedcmd.equalsIgnoreCase("tp")) {
                if (PlotMe.allowWorldTeleport) {
                    p.sendMessage(this.GREEN + " /plotme " + this.C("CommandTp") + " <" + this.C("WordId") + "> [" + this.C("WordWorld") + "]");
                }
                else {
                    p.sendMessage(this.GREEN + " /plotme " + this.C("CommandTp") + " <" + this.C("WordId") + ">");
                }
                p.sendMessage(this.AQUA + " " + this.C("HelpTp"));
            }
            else if (allowedcmd.equalsIgnoreCase("id")) {
                p.sendMessage(this.GREEN + " /plotme " + this.C("CommandId"));
                p.sendMessage(this.AQUA + " " + this.C("HelpId"));
            }
            else if (allowedcmd.equalsIgnoreCase("clear")) {
                p.sendMessage(this.GREEN + " /plotme " + this.C("CommandClear"));
                if (ecoon && pmi != null && pmi.ClearPrice != 0.0) {
                    p.sendMessage(this.AQUA + " " + this.C("HelpId") + " " + this.C("WordPrice") + " : " + this.RESET + this.round(pmi.ClearPrice));
                }
                else {
                    p.sendMessage(this.AQUA + " " + this.C("HelpClear"));
                }
            }
            else if (allowedcmd.equalsIgnoreCase("reset")) {
                p.sendMessage(this.GREEN + " /plotme " + this.C("CommandReset"));
                p.sendMessage(this.AQUA + " " + this.C("HelpReset"));
            }
            else if (allowedcmd.equalsIgnoreCase("add")) {
                p.sendMessage(this.GREEN + " /plotme " + this.C("CommandAdd") + " <" + this.C("WordPlayer") + ">");
                if (ecoon && pmi != null && pmi.AddPlayerPrice != 0.0) {
                    p.sendMessage(this.AQUA + " " + this.C("HelpAdd") + " " + this.C("WordPrice") + " : " + this.RESET + this.round(pmi.AddPlayerPrice));
                }
                else {
                    p.sendMessage(this.AQUA + " " + this.C("HelpAdd"));
                }
            }
            else if (allowedcmd.equalsIgnoreCase("deny")) {
                p.sendMessage(this.GREEN + " /plotme " + this.C("CommandDeny") + " <" + this.C("WordPlayer") + ">");
                if (ecoon && pmi != null && pmi.DenyPlayerPrice != 0.0) {
                    p.sendMessage(this.AQUA + " " + this.C("HelpDeny") + " " + this.C("WordPrice") + " : " + this.RESET + this.round(pmi.DenyPlayerPrice));
                }
                else {
                    p.sendMessage(this.AQUA + " " + this.C("HelpDeny"));
                }
            }
            else if (allowedcmd.equalsIgnoreCase("remove")) {
                p.sendMessage(this.GREEN + " /plotme " + this.C("CommandRemove") + " <" + this.C("WordPlayer") + ">");
                if (ecoon && pmi != null && pmi.RemovePlayerPrice != 0.0) {
                    p.sendMessage(this.AQUA + " " + this.C("HelpRemove") + " " + this.C("WordPrice") + " : " + this.RESET + this.round(pmi.RemovePlayerPrice));
                }
                else {
                    p.sendMessage(this.AQUA + " " + this.C("HelpRemove"));
                }
            }
            else if (allowedcmd.equalsIgnoreCase("undeny")) {
                p.sendMessage(this.GREEN + " /plotme " + this.C("CommandUndeny") + " <" + this.C("WordPlayer") + ">");
                if (ecoon && pmi != null && pmi.UndenyPlayerPrice != 0.0) {
                    p.sendMessage(this.AQUA + " " + this.C("HelpUndeny") + " " + this.C("WordPrice") + " : " + this.RESET + this.round(pmi.UndenyPlayerPrice));
                }
                else {
                    p.sendMessage(this.AQUA + " " + this.C("HelpUndeny"));
                }
            }
            else if (allowedcmd.equalsIgnoreCase("setowner")) {
                p.sendMessage(this.GREEN + " /plotme " + this.C("CommandSetowner") + " <" + this.C("WordPlayer") + ">");
                p.sendMessage(this.AQUA + " " + this.C("HelpSetowner"));
            }
            else if (allowedcmd.equalsIgnoreCase("move")) {
                p.sendMessage(this.GREEN + " /plotme " + this.C("CommandMove") + " <" + this.C("WordIdFrom") + "> <" + this.C("WordIdTo") + ">");
                p.sendMessage(this.AQUA + " " + this.C("HelpMove"));
            }
            else if (allowedcmd.equalsIgnoreCase("weanywhere")) {
                p.sendMessage(this.GREEN + " /plotme " + this.C("CommandWEAnywhere"));
                p.sendMessage(this.AQUA + " " + this.C("HelpWEAnywhere"));
            }
            else if (allowedcmd.equalsIgnoreCase("expired")) {
                p.sendMessage(this.GREEN + " /plotme " + this.C("CommandExpired") + " [page]");
                p.sendMessage(this.AQUA + " " + this.C("HelpExpired"));
            }
            else if (allowedcmd.equalsIgnoreCase("donelist")) {
                p.sendMessage(this.GREEN + " /plotme " + this.C("CommandDoneList") + " [page]");
                p.sendMessage(this.AQUA + " " + this.C("HelpDoneList"));
            }
            else if (allowedcmd.equalsIgnoreCase("addtime")) {
                p.sendMessage(this.GREEN + " /plotme " + this.C("CommandAddtime"));
                final int days = (pmi == null) ? 0 : pmi.DaysToExpiration;
                if (days == 0) {
                    p.sendMessage(this.AQUA + " " + this.C("HelpAddTime1") + " " + this.RESET + this.C("WordNever"));
                }
                else {
                    p.sendMessage(this.AQUA + " " + this.C("HelpAddTime1") + " " + this.RESET + days + this.AQUA + " " + this.C("HelpAddTime2"));
                }
            }
            else if (allowedcmd.equalsIgnoreCase("reload")) {
                p.sendMessage(this.GREEN + " /plotme reload");
                p.sendMessage(this.AQUA + " " + this.C("HelpReload"));
            }
            else if (allowedcmd.equalsIgnoreCase("dispose")) {
                p.sendMessage(this.GREEN + " /plotme " + this.C("CommandDispose"));
                if (ecoon && pmi != null && pmi.DisposePrice != 0.0) {
                    p.sendMessage(this.AQUA + " " + this.C("HelpDispose") + " " + this.C("WordPrice") + " : " + this.RESET + this.round(pmi.DisposePrice));
                }
                else {
                    p.sendMessage(this.AQUA + " " + this.C("HelpDispose"));
                }
            }
            else if (allowedcmd.equalsIgnoreCase("buy")) {
                p.sendMessage(this.GREEN + " /plotme " + this.C("CommandBuy"));
                p.sendMessage(this.AQUA + " " + this.C("HelpBuy"));
            }
            else if (allowedcmd.equalsIgnoreCase("sell")) {
                p.sendMessage(this.GREEN + " /plotme " + this.C("CommandSell") + " [" + this.C("WordAmount") + "]");
                p.sendMessage(this.AQUA + " " + this.C("HelpSell") + " " + this.C("WordDefault") + " : " + this.RESET + this.round(pmi.SellToPlayerPrice));
            }
            else if (allowedcmd.equalsIgnoreCase("sellbank")) {
                p.sendMessage(this.GREEN + " /plotme " + this.C("CommandSellBank"));
                p.sendMessage(this.AQUA + " " + this.C("HelpSellBank") + " " + this.RESET + this.round(pmi.SellToBankPrice));
            }
            else if (allowedcmd.equalsIgnoreCase("auction")) {
                p.sendMessage(this.GREEN + " /plotme " + this.C("CommandAuction") + " [" + this.C("WordAmount") + "]");
                p.sendMessage(this.AQUA + " " + this.C("HelpAuction") + " " + this.C("WordDefault") + " : " + this.RESET + "1");
            }
            else if (allowedcmd.equalsIgnoreCase("resetexpired")) {
                p.sendMessage(this.GREEN + " /plotme " + this.C("CommandResetExpired") + " <" + this.C("WordWorld") + ">");
                p.sendMessage(this.AQUA + " " + this.C("HelpResetExpired"));
            }
            else if (allowedcmd.equalsIgnoreCase("bid")) {
                p.sendMessage(this.GREEN + " /plotme " + this.C("CommandBid") + " <" + this.C("WordAmount") + ">");
                p.sendMessage(this.AQUA + " " + this.C("HelpBid"));
            }
        }
        return true;
    }
    
    private boolean tp(final Player p, final String[] args) {
        if (PlotMe.cPerms(p, "PlotMe.admin.tp")) {
            if (!PlotManager.isPlotWorld(p)) {
                if (!PlotMe.allowWorldTeleport) {
                    this.Send(p, this.RED + this.C("MsgNotPlotWorld"));
                }
                else if (args.length == 2 || args.length == 3) {
                    final String id = args[1];
                    if (!PlotManager.isValidId(id)) {
                        this.Send(p, this.C("WordUsage") + ": " + this.RED + "/plotme " + this.C("CommandTp") + " <" + this.C("WordId") + "> [" + this.C("WordWorld") + "] " + this.RESET + this.C("WordExample") + ": " + this.RED + "/plotme " + this.C("CommandTp") + " 5;-1 ");
                        return true;
                    }
                    World w;
                    if (args.length == 3) {
                        final String world = args[2];
                        w = Bukkit.getWorld(world);
                    }
                    else if (!PlotManager.isPlotWorld(p)) {
                        w = PlotManager.getFirstWorld();
                    }
                    else {
                        w = p.getWorld();
                    }
                    if (w == null || !PlotManager.isPlotWorld(w)) {
                        this.Send(p, this.RED + this.C("MsgNoPlotworldFound"));
                    }
                    else {
                        final Location bottom = PlotManager.getPlotBottomLoc(w, id);
                        final Location top = PlotManager.getPlotTopLoc(w, id);
                        p.teleport(new Location(w, bottom.getX() + (top.getBlockX() - bottom.getBlockX()) / 2, PlotManager.getMap(w).RoadHeight + 2, bottom.getZ() - 2.0));
                    }
                }
                else {
                    this.Send(p, this.C("WordUsage") + ": " + this.RED + "/plotme " + this.C("CommandTp") + " <" + this.C("WordId") + "> [" + this.C("WordWorld") + "] " + this.RESET + this.C("WordExample") + ": " + this.RED + "/plotme " + this.C("CommandTp") + " 5;-1 ");
                }
            }
            else if (args.length == 2 || (args.length == 3 && PlotMe.allowWorldTeleport)) {
                final String id = args[1];
                if (!PlotManager.isValidId(id)) {
                    if (PlotMe.allowWorldTeleport) {
                        this.Send(p, this.C("WordUsage") + ": " + this.RED + "/plotme " + this.C("CommandTp") + " <" + this.C("WordId") + "> [" + this.C("WordWorld") + "] " + this.RESET + this.C("WordExample") + ": " + this.RED + "/plotme " + this.C("CommandTp") + " 5;-1 ");
                    }
                    else {
                        this.Send(p, this.C("WordUsage") + ": " + this.RED + "/plotme " + this.C("CommandTp") + " <" + this.C("WordId") + "> " + this.RESET + this.C("WordExample") + ": " + this.RED + "/plotme " + this.C("CommandTp") + " 5;-1 ");
                    }
                    return true;
                }
                World w;
                if (args.length == 3) {
                    final String world = args[2];
                    w = Bukkit.getWorld(world);
                }
                else if (!PlotManager.isPlotWorld(p)) {
                    w = PlotManager.getFirstWorld();
                }
                else {
                    w = p.getWorld();
                }
                if (w == null || !PlotManager.isPlotWorld(w)) {
                    this.Send(p, this.RED + this.C("MsgNoPlotworldFound"));
                }
                else {
                    final Location bottom = PlotManager.getPlotBottomLoc(w, id);
                    final Location top = PlotManager.getPlotTopLoc(w, id);
                    p.teleport(new Location(w, bottom.getX() + (top.getBlockX() - bottom.getBlockX()) / 2, PlotManager.getMap(w).RoadHeight + 2, bottom.getZ() - 2.0));
                }
            }
            else if (PlotMe.allowWorldTeleport) {
                this.Send(p, this.C("WordUsage") + ": " + this.RED + "/plotme " + this.C("CommandTp") + " <" + this.C("WordId") + "> [" + this.C("WordWorld") + "] " + this.RESET + this.C("WordExample") + ": " + this.RED + "/plotme " + this.C("CommandTp") + " 5;-1 ");
            }
            else {
                this.Send(p, this.C("WordUsage") + ": " + this.RED + "/plotme " + this.C("CommandTp") + " <" + this.C("WordId") + "> " + this.RESET + this.C("WordExample") + ": " + this.RED + "/plotme " + this.C("CommandTp") + " 5;-1 ");
            }
        }
        else {
            this.Send(p, this.RED + this.C("MsgPermissionDenied"));
        }
        return true;
    }
    
    private boolean auto(final Player p, final String[] args) {
        if (PlotMe.cPerms(p, "PlotMe.use.auto")) {
            if (!PlotManager.isPlotWorld(p)) {
                if (!PlotMe.allowWorldTeleport) {
                    this.Send(p, this.RED + this.C("MsgNotPlotWorld"));
                }
                else {
                    World w;
                    if (!PlotManager.isPlotWorld(p)) {
                        if (args.length == 2) {
                            w = Bukkit.getWorld(args[1]);
                        }
                        else {
                            w = PlotManager.getFirstWorld();
                        }
                        if (w == null || !PlotManager.isPlotWorld(w)) {
                            this.Send(p, this.RED + args[1] + " " + this.C("MsgWorldNotPlot"));
                            return true;
                        }
                    }
                    else {
                        w = p.getWorld();
                    }
                    if (w == null) {
                        this.Send(p, this.RED + this.C("MsgNoPlotworldFound"));
                    }
                    else if (PlotManager.getNbOwnedPlot(p, w) >= PlotMe.getPlotLimit(p) && !PlotMe.cPerms(p, "PlotMe.admin")) {
                        this.Send(p, this.RED + this.C("MsgAlreadyReachedMaxPlots") + " (" + PlotManager.getNbOwnedPlot(p, w) + "/" + PlotMe.getPlotLimit(p) + "). " + this.C("WordUse") + " /plotme " + this.C("CommandHome") + " " + this.C("MsgToGetToIt"));
                    }
                    else {
                        final PlotMapInfo pmi = PlotManager.getMap(w);
                        final int limit = pmi.PlotAutoLimit;
                        for (int i = 0; i < limit; ++i) {
                            for (int x = -i; x <= i; ++x) {
                                for (int z = -i; z <= i; ++z) {
                                    final String id = "" + x + ";" + z;
                                    if (PlotManager.isPlotAvailable(id, w)) {
                                        final String name = p.getName();
                                        final UUID uuid = p.getUniqueId();
                                        double price = 0.0;
                                        if (PlotManager.isEconomyEnabled(w)) {
                                            price = pmi.ClaimPrice;
                                            final double balance = PlotMe.economy.getBalance(p);
                                            if (balance < price) {
                                                this.Send(p, this.RED + this.C("MsgNotEnoughAuto") + " " + this.C("WordMissing") + " " + this.RESET + this.f(price - balance, false));
                                                return true;
                                            }
                                            final EconomyResponse er = PlotMe.economy.withdrawPlayer(p, price);
                                            if (!er.transactionSuccess()) {
                                                this.Send(p, this.RED + er.errorMessage);
                                                this.warn(er.errorMessage);
                                                return true;
                                            }
                                        }
                                        final Plot plot = PlotManager.createPlot(w, id, name, uuid);
                                        p.teleport(new Location(w, PlotManager.bottomX(plot.id, w) + (PlotManager.topX(plot.id, w) - PlotManager.bottomX(plot.id, w)) / 2, pmi.RoadHeight + 2, PlotManager.bottomZ(plot.id, w) - 2));
                                        this.Send(p, this.C("MsgThisPlotYours") + " " + this.C("WordUse") + " " + this.RED + "/plotme " + this.C("CommandHome") + this.RESET + " " + this.C("MsgToGetToIt") + " " + this.f(-price));
                                        if (this.isAdv) {
                                            PlotMe.logger.info(this.LOG + name + " " + this.C("MsgClaimedPlot") + " " + id + ((price != 0.0) ? (" " + this.C("WordFor") + " " + price) : ""));
                                        }
                                        return true;
                                    }
                                }
                            }
                        }
                        this.Send(p, this.RED + this.C("MsgNoPlotFound1") + " " + (limit ^ 0x2) + " " + this.C("MsgNoPlotFound2"));
                    }
                }
            }
            else {
                World w;
                if (!PlotManager.isPlotWorld(p)) {
                    if (PlotMe.allowWorldTeleport) {
                        if (args.length == 2) {
                            w = Bukkit.getWorld(args[1]);
                        }
                        else {
                            w = PlotManager.getFirstWorld();
                        }
                        if (w == null || !PlotManager.isPlotWorld(w)) {
                            this.Send(p, this.RED + args[1] + " " + this.C("MsgWorldNotPlot"));
                            return true;
                        }
                    }
                    else {
                        w = p.getWorld();
                    }
                }
                else {
                    w = p.getWorld();
                }
                if (w == null) {
                    this.Send(p, this.RED + this.C("MsgNoPlotworldFound"));
                }
                else if (PlotManager.getNbOwnedPlot(p, w) >= PlotMe.getPlotLimit(p) && !PlotMe.cPerms(p, "PlotMe.admin")) {
                    this.Send(p, this.RED + this.C("MsgAlreadyReachedMaxPlots") + " (" + PlotManager.getNbOwnedPlot(p, w) + "/" + PlotMe.getPlotLimit(p) + "). " + this.C("WordUse") + " /plotme " + this.C("CommandHome") + " " + this.C("MsgToGetToIt"));
                }
                else {
                    final PlotMapInfo pmi = PlotManager.getMap(w);
                    final int limit = pmi.PlotAutoLimit;
                    for (int i = 0; i < limit; ++i) {
                        for (int x = -i; x <= i; ++x) {
                            for (int z = -i; z <= i; ++z) {
                                final String id = "" + x + ";" + z;
                                if (PlotManager.isPlotAvailable(id, w)) {
                                    final String name = p.getName();
                                    final UUID uuid = p.getUniqueId();
                                    double price = 0.0;
                                    if (PlotManager.isEconomyEnabled(w)) {
                                        price = pmi.ClaimPrice;
                                        final double balance = PlotMe.economy.getBalance(p);
                                        if (balance < price) {
                                            this.Send(p, this.RED + this.C("MsgNotEnoughAuto") + " " + this.C("WordMissing") + " " + this.RESET + this.f(price - balance, false));
                                            return true;
                                        }
                                        final EconomyResponse er = PlotMe.economy.withdrawPlayer(p, price);
                                        if (!er.transactionSuccess()) {
                                            this.Send(p, this.RED + er.errorMessage);
                                            this.warn(er.errorMessage);
                                            return true;
                                        }
                                    }
                                    final Plot plot = PlotManager.createPlot(w, id, name, uuid);
                                    p.teleport(new Location(w, PlotManager.bottomX(plot.id, w) + (PlotManager.topX(plot.id, w) - PlotManager.bottomX(plot.id, w)) / 2, pmi.RoadHeight + 2, PlotManager.bottomZ(plot.id, w) - 2));
                                    this.Send(p, this.C("MsgThisPlotYours") + " " + this.C("WordUse") + " " + this.RED + "/plotme " + this.C("CommandHome") + this.RESET + " " + this.C("MsgToGetToIt") + " " + this.f(-price));
                                    if (this.isAdv) {
                                        PlotMe.logger.info(this.LOG + name + " " + this.C("MsgClaimedPlot") + " " + id + ((price != 0.0) ? (" " + this.C("WordFor") + " " + price) : ""));
                                    }
                                    return true;
                                }
                            }
                        }
                    }
                    this.Send(p, this.RED + this.C("MsgNoPlotFound1") + " " + (limit ^ 0x2) + " " + this.C("MsgNoPlotFound2"));
                }
            }
        }
        else {
            this.Send(p, this.RED + this.C("MsgPermissionDenied"));
        }
        return true;
    }
    
    private boolean claim(final Player p, final String[] args) {
        if (PlotMe.cPerms(p, "PlotMe.use.claim") || PlotMe.cPerms(p, "PlotMe.admin.claim.other")) {
            if (!PlotManager.isPlotWorld(p)) {
                this.Send(p, this.RED + this.C("MsgNotPlotWorld"));
            }
            else {
                final String id = PlotManager.getPlotId(p.getLocation());
                if (id.equals("")) {
                    this.Send(p, this.RED + this.C("MsgCannotClaimRoad"));
                }
                else if (!PlotManager.isPlotAvailable(id, p)) {
                    this.Send(p, this.RED + this.C("MsgThisPlotOwned"));
                }
                else {
                    String playername = p.getName();
                    UUID uuid = p.getUniqueId();
                    if (args.length == 2 && PlotMe.cPerms(p, "PlotMe.admin.claim.other")) {
                        playername = args[1];
                        uuid = null;
                    }
                    final int plotlimit = PlotMe.getPlotLimit(p);
                    if (playername.equals(p.getName()) && plotlimit != -1 && PlotManager.getNbOwnedPlot(p) >= plotlimit) {
                        this.Send(p, this.RED + this.C("MsgAlreadyReachedMaxPlots") + " (" + PlotManager.getNbOwnedPlot(p) + "/" + PlotMe.getPlotLimit(p) + "). " + this.C("WordUse") + " " + this.RED + "/plotme " + this.C("CommandHome") + this.RESET + " " + this.C("MsgToGetToIt"));
                    }
                    else {
                        final World w = p.getWorld();
                        final PlotMapInfo pmi = PlotManager.getMap(w);
                        double price = 0.0;
                        if (PlotManager.isEconomyEnabled(w)) {
                            price = pmi.ClaimPrice;
                            final double balance = PlotMe.economy.getBalance(p);
                            if (balance < price) {
                                this.Send(p, this.RED + this.C("MsgNotEnoughBuy") + " " + this.C("WordMissing") + " " + this.RESET + (price - balance) + this.RED + " " + PlotMe.economy.currencyNamePlural());
                                return true;
                            }
                            final EconomyResponse er = PlotMe.economy.withdrawPlayer(p, price);
                            if (!er.transactionSuccess()) {
                                this.Send(p, this.RED + er.errorMessage);
                                this.warn(er.errorMessage);
                                return true;
                            }
                        }
                        final Plot plot = PlotManager.createPlot(w, id, playername, uuid);
                        if (plot == null) {
                            this.Send(p, this.RED + this.C("ErrCreatingPlotAt") + " " + id);
                        }
                        else {
                            if (playername.equalsIgnoreCase(p.getName())) {
                                this.Send(p, this.C("MsgThisPlotYours") + " " + this.C("WordUse") + " " + this.RED + "/plotme " + this.C("CommandHome") + this.RESET + " " + this.C("MsgToGetToIt") + " " + this.f(-price));
                            }
                            else {
                                this.Send(p, this.C("MsgThisPlotIsNow") + " " + playername + this.C("WordPossessive") + ". " + this.C("WordUse") + " " + this.RED + "/plotme " + this.C("CommandHome") + this.RESET + " " + this.C("MsgToGetToIt") + " " + this.f(-price));
                            }
                            if (this.isAdv) {
                                PlotMe.logger.info(this.LOG + playername + " " + this.C("MsgClaimedPlot") + " " + id + ((price != 0.0) ? (" " + this.C("WordFor") + " " + price) : ""));
                            }
                        }
                    }
                }
            }
        }
        else {
            this.Send(p, this.RED + this.C("MsgPermissionDenied"));
        }
        return true;
    }
    
    private boolean home(final Player p, final String[] args) {
        if (PlotMe.cPerms(p, "PlotMe.use.home") || PlotMe.cPerms(p, "PlotMe.admin.home.other")) {
            if (!PlotManager.isPlotWorld(p)) {
                if (!PlotMe.allowWorldTeleport) {
                    this.Send(p, this.RED + this.C("MsgNotPlotWorld"));
                }
                else {
                    String playername = p.getName();
                    UUID uuid = p.getUniqueId();
                    int nb = 1;
                    String worldname = "";
                    World w;
                    if (!PlotManager.isPlotWorld(p)) {
                        w = PlotManager.getFirstWorld();
                    }
                    else {
                        w = p.getWorld();
                    }
                    if (w != null) {
                        worldname = w.getName();
                    }
                    if (args[0].contains(":")) {
                        try {
                            if (args[0].split(":").length == 1 || args[0].split(":")[1].equals("")) {
                                this.Send(p, this.C("WordUsage") + ": " + this.RED + "/plotme " + this.C("CommandHome") + ":# " + this.RESET + this.C("WordExample") + ": " + this.RED + "/plotme " + this.C("CommandHome") + ":1");
                                return true;
                            }
                            nb = Integer.parseInt(args[0].split(":")[1]);
                        }
                        catch (NumberFormatException ex) {
                            this.Send(p, this.C("WordUsage") + ": " + this.RED + "/plotme " + this.C("CommandHome") + ":# " + this.RESET + this.C("WordExample") + ": " + this.RED + "/plotme " + this.C("CommandHome") + ":1");
                            return true;
                        }
                    }
                    if (args.length == 2) {
                        if (Bukkit.getWorld(args[1]) == null) {
                            if (PlotMe.cPerms(p, "PlotMe.admin.home.other")) {
                                playername = args[1];
                                uuid = null;
                            }
                        }
                        else {
                            w = Bukkit.getWorld(args[1]);
                            worldname = args[1];
                        }
                    }
                    if (args.length == 3) {
                        if (Bukkit.getWorld(args[2]) == null) {
                            this.Send(p, this.RED + args[2] + " " + this.C("MsgWorldNotPlot"));
                            return true;
                        }
                        w = Bukkit.getWorld(args[2]);
                        worldname = args[2];
                    }
                    if (!PlotManager.isPlotWorld(w)) {
                        this.Send(p, this.RED + worldname + " " + this.C("MsgWorldNotPlot"));
                    }
                    else {
                        int i = nb - 1;
                        for (final Plot plot : PlotManager.getPlots(w).values()) {
                            if ((uuid == null && plot.owner.equalsIgnoreCase(playername)) || (uuid != null && plot.ownerId != null && plot.ownerId.equals(uuid))) {
                                if (i == 0) {
                                    final PlotMapInfo pmi = PlotManager.getMap(w);
                                    double price = 0.0;
                                    if (PlotManager.isEconomyEnabled(w)) {
                                        price = pmi.PlotHomePrice;
                                        final double balance = PlotMe.economy.getBalance(p);
                                        if (balance < price) {
                                            this.Send(p, this.RED + this.C("MsgNotEnoughTp") + " " + this.C("WordMissing") + " " + this.RESET + this.f(price - balance, false));
                                            return true;
                                        }
                                        final EconomyResponse er = PlotMe.economy.withdrawPlayer(p, price);
                                        if (!er.transactionSuccess()) {
                                            this.Send(p, this.RED + er.errorMessage);
                                            return true;
                                        }
                                    }
                                    p.teleport(PlotManager.getPlotHome(w, plot));
                                    if (price != 0.0) {
                                        this.Send(p, this.f(-price));
                                    }
                                    return true;
                                }
                                --i;
                            }
                        }
                        if (nb > 0) {
                            if (!playername.equalsIgnoreCase(p.getName())) {
                                this.Send(p, this.RED + playername + " " + this.C("MsgDoesNotHavePlot") + " #" + nb);
                            }
                            else {
                                this.Send(p, this.RED + this.C("MsgPlotNotFound") + " #" + nb);
                            }
                        }
                        else if (!playername.equalsIgnoreCase(p.getName())) {
                            this.Send(p, this.RED + playername + " " + this.C("MsgDoesNotHavePlot"));
                        }
                        else {
                            this.Send(p, this.RED + this.C("MsgYouHaveNoPlot"));
                        }
                    }
                }
            }
            else {
                String playername = p.getName();
                UUID uuid = p.getUniqueId();
                int nb = 1;
                String worldname = "";
                World w;
                if (!PlotManager.isPlotWorld(p) && PlotMe.allowWorldTeleport) {
                    w = PlotManager.getFirstWorld();
                }
                else {
                    w = p.getWorld();
                }
                if (w != null) {
                    worldname = w.getName();
                }
                if (args[0].contains(":")) {
                    try {
                        if (args[0].split(":").length == 1 || args[0].split(":")[1].equals("")) {
                            this.Send(p, this.C("WordUsage") + ": " + this.RED + "/plotme " + this.C("CommandHome") + ":# " + this.RESET + this.C("WordExample") + ": " + this.RED + "/plotme " + this.C("CommandHome") + ":1");
                            return true;
                        }
                        nb = Integer.parseInt(args[0].split(":")[1]);
                    }
                    catch (Exception ex2) {
                        this.Send(p, this.C("WordUsage") + ": " + this.RED + "/plotme " + this.C("CommandHome") + ":# " + this.RESET + this.C("WordExample") + ": " + this.RED + "/plotme " + this.C("CommandHome") + ":1");
                        return true;
                    }
                }
                if (args.length == 2) {
                    if (Bukkit.getWorld(args[1]) == null) {
                        if (PlotMe.cPerms(p, "PlotMe.admin.home.other")) {
                            playername = args[1];
                            uuid = null;
                        }
                    }
                    else {
                        w = Bukkit.getWorld(args[1]);
                        worldname = args[1];
                    }
                }
                if (args.length == 3) {
                    if (Bukkit.getWorld(args[2]) == null) {
                        this.Send(p, this.RED + args[2] + " " + this.C("MsgWorldNotPlot"));
                        return true;
                    }
                    w = Bukkit.getWorld(args[2]);
                    worldname = args[2];
                }
                if (!PlotManager.isPlotWorld(w)) {
                    this.Send(p, this.RED + worldname + " " + this.C("MsgWorldNotPlot"));
                }
                else {
                    int i = nb - 1;
                    for (final Plot plot : PlotManager.getPlots(w).values()) {
                        if ((uuid == null && plot.owner.equalsIgnoreCase(playername)) || (uuid != null && plot.ownerId != null && plot.ownerId.equals(uuid))) {
                            if (i == 0) {
                                final PlotMapInfo pmi = PlotManager.getMap(w);
                                double price = 0.0;
                                if (PlotManager.isEconomyEnabled(w)) {
                                    price = pmi.PlotHomePrice;
                                    final double balance = PlotMe.economy.getBalance(p);
                                    if (balance < price) {
                                        this.Send(p, this.RED + this.C("MsgNotEnoughTp") + " " + this.C("WordMissing") + " " + this.RESET + this.f(price - balance, false));
                                        return true;
                                    }
                                    final EconomyResponse er = PlotMe.economy.withdrawPlayer(p, price);
                                    if (!er.transactionSuccess()) {
                                        this.Send(p, this.RED + er.errorMessage);
                                        return true;
                                    }
                                }
                                p.teleport(PlotManager.getPlotHome(w, plot));
                                if (price != 0.0) {
                                    this.Send(p, this.f(-price));
                                }
                                return true;
                            }
                            --i;
                        }
                    }
                    if (nb > 0) {
                        if (!playername.equalsIgnoreCase(p.getName())) {
                            this.Send(p, this.RED + playername + " " + this.C("MsgDoesNotHavePlot") + " #" + nb);
                        }
                        else {
                            this.Send(p, this.RED + this.C("MsgPlotNotFound") + " #" + nb);
                        }
                    }
                    else if (!playername.equalsIgnoreCase(p.getName())) {
                        this.Send(p, this.RED + playername + " " + this.C("MsgDoesNotHavePlot"));
                    }
                    else {
                        this.Send(p, this.RED + this.C("MsgYouHaveNoPlot"));
                    }
                }
            }
        }
        else {
            this.Send(p, this.RED + this.C("MsgPermissionDenied"));
        }
        return true;
    }
    
    private boolean info(final Player p, final String[] args) {
        if (PlotMe.cPerms(p, "PlotMe.use.info")) {
            if (!PlotManager.isPlotWorld(p)) {
                this.Send(p, this.RED + this.C("MsgNotPlotWorld"));
            }
            else {
                final String id = PlotManager.getPlotId(p.getLocation());
                if (id.equals("")) {
                    this.Send(p, this.RED + this.C("MsgNoPlotFound"));
                }
                else if (!PlotManager.isPlotAvailable(id, p)) {
                    final Plot plot = PlotManager.getPlotById(p, id);
                    p.sendMessage(this.GREEN + this.C("InfoId") + ": " + this.AQUA + id + this.GREEN + " " + this.C("InfoOwner") + ": " + this.AQUA + plot.owner + this.GREEN + " " + this.C("InfoBiome") + ": " + this.AQUA + this.FormatBiome(plot.biome.name()));
                    p.sendMessage(this.GREEN + this.C("InfoExpire") + ": " + this.AQUA + ((plot.expireddate == null) ? this.C("WordNever") : plot.expireddate.toString()) + this.GREEN + " " + this.C("InfoFinished") + ": " + this.AQUA + (plot.finished ? this.C("WordYes") : this.C("WordNo")) + this.GREEN + " " + this.C("InfoProtected") + ": " + this.AQUA + (plot.protect ? this.C("WordYes") : this.C("WordNo")));
                    if (plot.allowedcount() > 0) {
                        p.sendMessage(this.GREEN + this.C("InfoHelpers") + ": " + this.AQUA + plot.getAllowed());
                    }
                    if (PlotMe.allowToDeny && plot.deniedcount() > 0) {
                        p.sendMessage(this.GREEN + this.C("InfoDenied") + ": " + this.AQUA + plot.getDenied());
                    }
                    if (PlotManager.isEconomyEnabled(p)) {
                        if (plot.currentbidder == null || plot.currentbidder.equalsIgnoreCase("")) {
                            p.sendMessage(this.GREEN + this.C("InfoAuctionned") + ": " + this.AQUA + (plot.auctionned ? (this.C("WordYes") + this.GREEN + " " + this.C("InfoMinimumBid") + ": " + this.AQUA + this.round(plot.currentbid)) : this.C("WordNo")) + this.GREEN + " " + this.C("InfoForSale") + ": " + this.AQUA + (plot.forsale ? (this.AQUA + this.round(plot.customprice)) : this.C("WordNo")));
                        }
                        else {
                            p.sendMessage(this.GREEN + this.C("InfoAuctionned") + ": " + this.AQUA + (plot.auctionned ? (this.C("WordYes") + this.GREEN + " " + this.C("InfoBidder") + ": " + this.AQUA + plot.currentbidder + this.GREEN + " " + this.C("InfoBid") + ": " + this.AQUA + this.round(plot.currentbid)) : this.C("WordNo")) + this.GREEN + " " + this.C("InfoForSale") + ": " + this.AQUA + (plot.forsale ? (this.AQUA + this.round(plot.customprice)) : this.C("WordNo")));
                        }
                    }
                }
                else {
                    this.Send(p, this.RED + this.C("MsgThisPlot") + "(" + id + ") " + this.C("MsgHasNoOwner"));
                }
            }
        }
        else {
            this.Send(p, this.RED + this.C("MsgPermissionDenied"));
        }
        return true;
    }
    
    private boolean comment(final Player p, final String[] args) {
        if (PlotMe.cPerms(p, "PlotMe.use.comment")) {
            if (!PlotManager.isPlotWorld(p)) {
                this.Send(p, this.RED + this.C("MsgNotPlotWorld"));
            }
            else if (args.length < 2) {
                this.Send(p, this.C("WordUsage") + ": " + this.RED + "/plotme " + this.C("CommandComment") + " <" + this.C("WordText") + ">");
            }
            else {
                final String id = PlotManager.getPlotId(p.getLocation());
                if (id.equals("")) {
                    this.Send(p, this.RED + this.C("MsgNoPlotFound"));
                }
                else if (!PlotManager.isPlotAvailable(id, p)) {
                    final World w = p.getWorld();
                    final PlotMapInfo pmi = PlotManager.getMap(w);
                    final String playername = p.getName();
                    final UUID uuid = p.getUniqueId();
                    double price = 0.0;
                    if (PlotManager.isEconomyEnabled(w)) {
                        price = pmi.AddCommentPrice;
                        final double balance = PlotMe.economy.getBalance(p);
                        if (balance < price) {
                            this.Send(p, this.RED + this.C("MsgNotEnoughComment") + " " + this.C("WordMissing") + " " + this.RESET + this.f(price - balance, false));
                            return true;
                        }
                        final EconomyResponse er = PlotMe.economy.withdrawPlayer(p, price);
                        if (!er.transactionSuccess()) {
                            this.Send(p, this.RED + er.errorMessage);
                            this.warn(er.errorMessage);
                            return true;
                        }
                    }
                    final Plot plot = PlotManager.getPlotById(p, id);
                    String text = StringUtils.join(args, " ");
                    text = text.substring(text.indexOf(" "));
                    final String[] comment = { playername, text, uuid.toString() };
                    plot.comments.add(comment);
                    SqlManager.addPlotComment(comment, plot.comments.size(), PlotManager.getIdX(id), PlotManager.getIdZ(id), plot.world, uuid);
                    this.Send(p, this.C("MsgCommentAdded") + " " + this.f(-price));
                    if (this.isAdv) {
                        PlotMe.logger.info(this.LOG + playername + " " + this.C("MsgCommentedPlot") + " " + id + ((price != 0.0) ? (" " + this.C("WordFor") + " " + price) : ""));
                    }
                }
                else {
                    this.Send(p, this.RED + this.C("MsgThisPlot") + "(" + id + ") " + this.C("MsgHasNoOwner"));
                }
            }
        }
        else {
            this.Send(p, this.RED + this.C("MsgPermissionDenied"));
        }
        return true;
    }
    
    private boolean comments(final Player p, final String[] args) {
        if (PlotMe.cPerms(p, "PlotMe.use.comments")) {
            if (!PlotManager.isPlotWorld(p)) {
                this.Send(p, this.RED + this.C("MsgNotPlotWorld"));
            }
            else if (args.length < 2) {
                final String id = PlotManager.getPlotId(p.getLocation());
                if (id.equals("")) {
                    this.Send(p, this.RED + this.C("MsgNoPlotFound"));
                }
                else if (!PlotManager.isPlotAvailable(id, p)) {
                    final Plot plot = PlotManager.getPlotById(p, id);
                    if (plot.ownerId.equals(p.getUniqueId()) || plot.isAllowed(p.getUniqueId()) || PlotMe.cPerms(p, "PlotMe.admin")) {
                        if (plot.comments.size() == 0) {
                            this.Send(p, this.C("MsgNoComments"));
                        }
                        else {
                            this.Send(p, this.C("MsgYouHave") + " " + this.BLUE + plot.comments.size() + this.RESET + " " + this.C("MsgComments"));
                            for (final String[] comment : plot.comments) {
                                p.sendMessage(ChatColor.BLUE + this.C("WordFrom") + " : " + this.RED + comment[0]);
                                p.sendMessage("" + this.RESET + ChatColor.ITALIC + comment[1]);
                            }
                        }
                    }
                    else {
                        this.Send(p, this.RED + this.C("MsgThisPlot") + "(" + id + ") " + this.C("MsgNotYoursNotAllowedViewComments"));
                    }
                }
                else {
                    this.Send(p, this.RED + this.C("MsgThisPlot") + "(" + id + ") " + this.C("MsgHasNoOwner"));
                }
            }
        }
        else {
            p.sendMessage(this.BLUE + this.PREFIX + this.RED + this.C("MsgPermissionDenied"));
        }
        return true;
    }
    
    private boolean biome(final Player p, final String[] args) {
        if (PlotMe.cPerms(p, "PlotMe.use.biome")) {
            if (!PlotManager.isPlotWorld(p)) {
                this.Send(p, this.RED + this.C("MsgNotPlotWorld"));
            }
            else {
                final String id = PlotManager.getPlotId(p.getLocation());
                if (id.equals("")) {
                    p.sendMessage(this.BLUE + this.PREFIX + this.RED + this.C("MsgNoPlotFound"));
                }
                else if (!PlotManager.isPlotAvailable(id, p)) {
                    final World w = p.getWorld();
                    if (args.length == 2) {
                        Biome biome = null;
                        for (final Biome bio : Biome.values()) {
                            if (bio.name().equalsIgnoreCase(args[1])) {
                                biome = bio;
                            }
                        }
                        if (biome == null) {
                            this.Send(p, this.RED + args[1] + this.RESET + " " + this.C("MsgIsInvalidBiome"));
                        }
                        else {
                            final Plot plot = PlotManager.getPlotById(p, id);
                            final String playername = p.getName();
                            if (plot.owner.equalsIgnoreCase(playername) || PlotMe.cPerms(p, "PlotMe.admin")) {
                                final PlotMapInfo pmi = PlotManager.getMap(w);
                                double price = 0.0;
                                if (PlotManager.isEconomyEnabled(w)) {
                                    price = pmi.BiomeChangePrice;
                                    final double balance = PlotMe.economy.getBalance(p);
                                    if (balance < price) {
                                        this.Send(p, this.RED + this.C("MsgNotEnoughBiome") + " " + this.C("WordMissing") + " " + this.RESET + this.f(price - balance, false));
                                        return true;
                                    }
                                    final EconomyResponse er = PlotMe.economy.withdrawPlayer(p, price);
                                    if (!er.transactionSuccess()) {
                                        this.Send(p, this.RED + er.errorMessage);
                                        this.warn(er.errorMessage);
                                        return true;
                                    }
                                }
                                PlotManager.setBiome(w, id, plot, biome);
                                this.Send(p, this.C("MsgBiomeSet") + " " + ChatColor.BLUE + this.FormatBiome(biome.name()) + " " + this.f(-price));
                                if (this.isAdv) {
                                    PlotMe.logger.info(this.LOG + playername + " " + this.C("MsgChangedBiome") + " " + id + " " + this.C("WordTo") + " " + this.FormatBiome(biome.name()) + ((price != 0.0) ? (" " + this.C("WordFor") + " " + price) : ""));
                                }
                            }
                            else {
                                this.Send(p, this.RED + this.C("MsgThisPlot") + "(" + id + ") " + this.C("MsgNotYoursNotAllowedBiome"));
                            }
                        }
                    }
                    else {
                        final Plot plot2 = PlotMe.plotmaps.get(w.getName().toLowerCase()).plots.get(id);
                        this.Send(p, this.C("MsgPlotUsingBiome") + " " + ChatColor.BLUE + this.FormatBiome(plot2.biome.name()));
                    }
                }
                else {
                    this.Send(p, this.RED + this.C("MsgThisPlot") + "(" + id + ") " + this.C("MsgHasNoOwner"));
                }
            }
        }
        else {
            this.Send(p, this.RED + this.C("MsgPermissionDenied"));
        }
        return true;
    }
    
    private boolean biomelist(final Player player, final String[] args) {
        if (PlotMe.cPerms(player, "PlotMe.use.biome")) {
            this.Send(player, this.C("WordBiomes") + " : ");
            StringBuilder line = new StringBuilder();
            final List<String> biomes = new ArrayList<>();
            for (final Biome b : Biome.values()) {
                biomes.add(b.name());
            }
            Collections.sort(biomes);
            final List<String> column1 = new ArrayList<>();
            final List<String> column2 = new ArrayList<>();
            final List<String> column3 = new ArrayList<>();
            for (int ctr = 0; ctr < biomes.size(); ++ctr) {
                if (ctr < biomes.size() / 3) {
                    column1.add(biomes.get(ctr));
                }
                else if (ctr < biomes.size() * 2 / 3) {
                    column2.add(biomes.get(ctr));
                }
                else {
                    column3.add(biomes.get(ctr));
                }
            }
            for (int ctr = 0; ctr < column1.size(); ++ctr) {
                String b2 = this.FormatBiome(column1.get(ctr));
                int nameLength = MinecraftFontWidthCalculator.getStringWidth(b2);
                line.append(b2).append(this.whitespace(432 - nameLength));
                if (ctr < column2.size()) {
                    b2 = this.FormatBiome(column2.get(ctr));
                    nameLength = MinecraftFontWidthCalculator.getStringWidth(b2);
                    line.append(b2).append(this.whitespace(432 - nameLength));
                }
                if (ctr < column3.size()) {
                    b2 = this.FormatBiome(column3.get(ctr));
                    line.append(b2);
                }
                player.sendMessage("" + this.BLUE + line);
                line = new StringBuilder();
            }
        }
        else {
            this.Send(player, this.RED + this.C("MsgPermissionDenied"));
        }
        return true;
    }
    
    private boolean reset(final Player p, final String[] args) {
        if (PlotMe.cPerms(p, "PlotMe.admin.reset")) {
            if (!PlotManager.isPlotWorld(p)) {
                this.Send(p, this.RED + this.C("MsgNotPlotWorld"));
            }
            else {
                final Plot plot = PlotManager.getPlotById(p.getLocation());
                if (plot == null) {
                    this.Send(p, this.RED + this.C("MsgNoPlotFound"));
                }
                else if (plot.protect) {
                    this.Send(p, this.RED + this.C("MsgPlotProtectedCannotReset"));
                }
                else {
                    final String id = plot.id;
                    final World w = p.getWorld();
                    PlotManager.setBiome(w, id, plot, Biome.PLAINS);
                    PlotManager.clear(w, plot);
                    if (PlotManager.isEconomyEnabled(p)) {
                        if (plot.auctionned) {
                            final String currentbidder = plot.currentbidder;
                            if (!currentbidder.equals("")) {
                                final OfflinePlayer playercurrentbidder = Bukkit.getOfflinePlayer(plot.currentbidderId);
                                final EconomyResponse er = PlotMe.economy.depositPlayer(playercurrentbidder, plot.currentbid);
                                if (!er.transactionSuccess()) {
                                    this.Send(p, er.errorMessage);
                                    this.warn(er.errorMessage);
                                }
                                else {
                                    for (final Player player : Bukkit.getServer().getOnlinePlayers()) {
                                        if (player.getName().equalsIgnoreCase(currentbidder)) {
                                            this.Send(player, this.C("WordPlot") + " " + id + " " + this.C("MsgOwnedBy") + " " + plot.owner + " " + this.C("MsgWasReset") + " " + this.f(plot.currentbid));
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                        final PlotMapInfo pmi = PlotManager.getMap(p);
                        if (pmi.RefundClaimPriceOnReset) {
                            final OfflinePlayer playerowner = Bukkit.getOfflinePlayer(plot.ownerId);
                            final EconomyResponse er = PlotMe.economy.depositPlayer(playerowner, pmi.ClaimPrice);
                            if (!er.transactionSuccess()) {
                                this.Send(p, this.RED + er.errorMessage);
                                this.warn(er.errorMessage);
                                return true;
                            }
                            for (final Player player : Bukkit.getServer().getOnlinePlayers()) {
                                if (player.getName().equalsIgnoreCase(plot.owner)) {
                                    this.Send(player, this.C("WordPlot") + " " + id + " " + this.C("MsgOwnedBy") + " " + plot.owner + " " + this.C("MsgWasReset") + " " + this.f(pmi.ClaimPrice));
                                    break;
                                }
                            }
                        }
                    }
                    if (!PlotManager.isPlotAvailable(id, p)) {
                        PlotManager.getPlots(p).remove(id);
                    }
                    final String name = p.getName();
                    PlotManager.removeOwnerSign(w, id);
                    PlotManager.removeSellSign(w, id);
                    SqlManager.deletePlot(PlotManager.getIdX(id), PlotManager.getIdZ(id), w.getName().toLowerCase());
                    this.Send(p, this.C("MsgPlotReset"));
                    if (this.isAdv) {
                        PlotMe.logger.info(this.LOG + name + " " + this.C("MsgResetPlot") + " " + id);
                    }
                }
            }
        }
        else {
            this.Send(p, this.RED + this.C("MsgPermissionDenied"));
        }
        return true;
    }
    
    private boolean clear(final Player p, final String[] args) {
        if (PlotMe.cPerms(p, "PlotMe.admin.clear") || PlotMe.cPerms(p, "PlotMe.use.clear")) {
            if (!PlotManager.isPlotWorld(p)) {
                this.Send(p, this.RED + this.C("MsgNotPlotWorld"));
            }
            else {
                final String id = PlotManager.getPlotId(p.getLocation());
                if (id.equals("")) {
                    this.Send(p, this.RED + this.C("MsgNoPlotFound"));
                }
                else if (!PlotManager.isPlotAvailable(id, p)) {
                    final Plot plot = PlotManager.getPlotById(p, id);
                    if (plot.protect) {
                        this.Send(p, this.RED + this.C("MsgPlotProtectedCannotClear"));
                    }
                    else {
                        final String playername = p.getName();
                        if (plot.owner.equalsIgnoreCase(playername) || PlotMe.cPerms(p, "PlotMe.admin.clear")) {
                            final World w = p.getWorld();
                            final PlotMapInfo pmi = PlotManager.getMap(w);
                            double price = 0.0;
                            if (PlotManager.isEconomyEnabled(w)) {
                                price = pmi.ClearPrice;
                                final double balance = PlotMe.economy.getBalance(p);
                                if (balance < price) {
                                    this.Send(p, this.RED + this.C("MsgNotEnoughClear") + " " + this.C("WordMissing") + " " + this.RESET + (price - balance) + this.RED + " " + PlotMe.economy.currencyNamePlural());
                                    return true;
                                }
                                final EconomyResponse er = PlotMe.economy.withdrawPlayer(p, price);
                                if (!er.transactionSuccess()) {
                                    this.Send(p, this.RED + er.errorMessage);
                                    this.warn(er.errorMessage);
                                    return true;
                                }
                            }
                            PlotManager.clear(w, plot);
                            this.Send(p, this.C("MsgPlotCleared") + " " + this.f(-price));
                            if (this.isAdv) {
                                PlotMe.logger.info(this.LOG + playername + " " + this.C("MsgClearedPlot") + " " + id + ((price != 0.0) ? (" " + this.C("WordFor") + " " + price) : ""));
                            }
                        }
                        else {
                            this.Send(p, this.RED + this.C("MsgThisPlot") + "(" + id + ") " + this.C("MsgNotYoursNotAllowedClear"));
                        }
                    }
                }
                else {
                    this.Send(p, this.RED + this.C("MsgThisPlot") + "(" + id + ") " + this.C("MsgHasNoOwner"));
                }
            }
        }
        else {
            this.Send(p, this.RED + this.C("MsgPermissionDenied"));
        }
        return true;
    }
    
    private boolean add(final Player p, final String[] args) {
        if (PlotMe.cPerms(p, "PlotMe.admin.add") || PlotMe.cPerms(p, "PlotMe.use.add")) {
            if (!PlotManager.isPlotWorld(p)) {
                this.Send(p, this.RED + this.C("MsgNotPlotWorld"));
            }
            else {
                final String id = PlotManager.getPlotId(p.getLocation());
                if (id.equals("")) {
                    this.Send(p, this.RED + this.C("MsgNoPlotFound"));
                }
                else if (!PlotManager.isPlotAvailable(id, p)) {
                    if (args.length < 2 || args[1].equalsIgnoreCase("")) {
                        this.Send(p, this.C("WordUsage") + " " + this.RED + "/plotme " + this.C("CommandAdd") + " <" + this.C("WordPlayer") + ">");
                    }
                    else {
                        final Plot plot = PlotManager.getPlotById(p, id);
                        final String playername = p.getName();
                        final String allowed = args[1];
                        if (plot.owner.equalsIgnoreCase(playername) || PlotMe.cPerms(p, "PlotMe.admin.add")) {
                            if (plot.isAllowedConsulting(allowed) || plot.isGroupAllowed(allowed)) {
                                this.Send(p, this.C("WordPlayer") + " " + this.RED + args[1] + this.RESET + " " + this.C("MsgAlreadyAllowed"));
                            }
                            else {
                                final World w = p.getWorld();
                                final PlotMapInfo pmi = PlotManager.getMap(w);
                                double price = 0.0;
                                if (PlotManager.isEconomyEnabled(w)) {
                                    price = pmi.AddPlayerPrice;
                                    final double balance = PlotMe.economy.getBalance(p);
                                    if (balance < price) {
                                        this.Send(p, this.RED + this.C("MsgNotEnoughAdd") + " " + this.C("WordMissing") + " " + this.RESET + this.f(price - balance, false));
                                        return true;
                                    }
                                    final EconomyResponse er = PlotMe.economy.withdrawPlayer(p, price);
                                    if (!er.transactionSuccess()) {
                                        this.Send(p, this.RED + er.errorMessage);
                                        this.warn(er.errorMessage);
                                        return true;
                                    }
                                }
                                plot.addAllowed(allowed);
                                plot.removeDenied(allowed);
                                this.Send(p, this.C("WordPlayer") + " " + this.RED + allowed + this.RESET + " " + this.C("MsgNowAllowed") + " " + this.f(-price));
                                if (this.isAdv) {
                                    PlotMe.logger.info(this.LOG + playername + " " + this.C("MsgAddedPlayer") + " " + allowed + " " + this.C("MsgToPlot") + " " + id + ((price != 0.0) ? (" " + this.C("WordFor") + " " + price) : ""));
                                }
                            }
                        }
                        else {
                            this.Send(p, this.RED + this.C("MsgThisPlot") + "(" + id + ") " + this.C("MsgNotYoursNotAllowedAdd"));
                        }
                    }
                }
                else {
                    this.Send(p, this.RED + this.C("MsgThisPlot") + "(" + id + ") " + this.C("MsgHasNoOwner"));
                }
            }
        }
        else {
            this.Send(p, this.RED + this.C("MsgPermissionDenied"));
        }
        return true;
    }
    
    private boolean deny(final Player p, final String[] args) {
        if (PlotMe.cPerms(p, "PlotMe.admin.deny") || PlotMe.cPerms(p, "PlotMe.use.deny")) {
            if (!PlotManager.isPlotWorld(p)) {
                this.Send(p, this.RED + this.C("MsgNotPlotWorld"));
            }
            else {
                final String id = PlotManager.getPlotId(p.getLocation());
                if (id.equals("")) {
                    this.Send(p, this.RED + this.C("MsgNoPlotFound"));
                }
                else if (!PlotManager.isPlotAvailable(id, p)) {
                    if (args.length < 2 || args[1].equalsIgnoreCase("")) {
                        this.Send(p, this.C("WordUsage") + " " + this.RED + "/plotme " + this.C("CommandDeny") + " <" + this.C("WordPlayer") + ">");
                    }
                    else {
                        final Plot plot = PlotManager.getPlotById(p, id);
                        final String playername = p.getName();
                        final String denied = args[1];
                        if (plot.owner.equalsIgnoreCase(playername) || PlotMe.cPerms(p, "PlotMe.admin.deny")) {
                            if (plot.isDeniedConsulting(denied) || plot.isGroupDenied(denied)) {
                                this.Send(p, this.C("WordPlayer") + " " + this.RED + args[1] + this.RESET + " " + this.C("MsgAlreadyDenied"));
                            }
                            else {
                                final World w = p.getWorld();
                                final PlotMapInfo pmi = PlotManager.getMap(w);
                                double price = 0.0;
                                if (PlotManager.isEconomyEnabled(w)) {
                                    price = pmi.DenyPlayerPrice;
                                    final double balance = PlotMe.economy.getBalance(p);
                                    if (balance < price) {
                                        this.Send(p, this.RED + this.C("MsgNotEnoughDeny") + " " + this.C("WordMissing") + " " + this.RESET + this.f(price - balance, false));
                                        return true;
                                    }
                                    final EconomyResponse er = PlotMe.economy.withdrawPlayer(p, price);
                                    if (!er.transactionSuccess()) {
                                        this.Send(p, this.RED + er.errorMessage);
                                        this.warn(er.errorMessage);
                                        return true;
                                    }
                                }
                                plot.addDenied(denied);
                                plot.removeAllowed(denied);
                                if (denied.equals("*")) {
                                    final List<Player> deniedplayers = PlotManager.getPlayersInPlot(w, id);
                                    for (final Player deniedplayer : deniedplayers) {
                                        if (!plot.isAllowed(deniedplayer.getUniqueId())) {
                                            deniedplayer.teleport(PlotManager.getPlotHome(w, plot));
                                        }
                                    }
                                }
                                else {
                                    final Player deniedplayer2 = Bukkit.getPlayerExact(denied);
                                    if (deniedplayer2 != null && deniedplayer2.getWorld().equals(w)) {
                                        final String deniedid = PlotManager.getPlotId(deniedplayer2);
                                        if (deniedid.equalsIgnoreCase(id)) {
                                            deniedplayer2.teleport(PlotManager.getPlotHome(w, plot));
                                        }
                                    }
                                }
                                this.Send(p, this.C("WordPlayer") + " " + this.RED + denied + this.RESET + " " + this.C("MsgNowDenied") + " " + this.f(-price));
                                if (this.isAdv) {
                                    PlotMe.logger.info(this.LOG + playername + " " + this.C("MsgDeniedPlayer") + " " + denied + " " + this.C("MsgToPlot") + " " + id + ((price != 0.0) ? (" " + this.C("WordFor") + " " + price) : ""));
                                }
                            }
                        }
                        else {
                            this.Send(p, this.RED + this.C("MsgThisPlot") + "(" + id + ") " + this.C("MsgNotYoursNotAllowedDeny"));
                        }
                    }
                }
                else {
                    this.Send(p, this.RED + this.C("MsgThisPlot") + "(" + id + ") " + this.C("MsgHasNoOwner"));
                }
            }
        }
        else {
            this.Send(p, this.RED + this.C("MsgPermissionDenied"));
        }
        return true;
    }
    
    private boolean remove(final Player p, final String[] args) {
        if (PlotMe.cPerms(p, "PlotMe.admin.remove") || PlotMe.cPerms(p, "PlotMe.use.remove")) {
            if (!PlotManager.isPlotWorld(p)) {
                this.Send(p, this.RED + this.C("MsgNotPlotWorld"));
            }
            else {
                final String id = PlotManager.getPlotId(p.getLocation());
                if (id.equals("")) {
                    this.Send(p, this.RED + this.C("MsgNoPlotFound"));
                }
                else if (!PlotManager.isPlotAvailable(id, p)) {
                    if (args.length < 2 || args[1].equalsIgnoreCase("")) {
                        this.Send(p, this.C("WordUsage") + ": " + this.RED + "/plotme " + this.C("CommandRemove") + " <" + this.C("WordPlayer") + ">");
                    }
                    else {
                        final Plot plot = PlotManager.getPlotById(p, id);
                        final UUID playeruuid = p.getUniqueId();
                        final String allowed = args[1];
                        if (plot.ownerId.equals(playeruuid) || PlotMe.cPerms(p, "PlotMe.admin.remove")) {
                            if (plot.isAllowedConsulting(allowed) || plot.isGroupAllowed(allowed)) {
                                final World w = p.getWorld();
                                final PlotMapInfo pmi = PlotManager.getMap(w);
                                double price = 0.0;
                                if (PlotManager.isEconomyEnabled(w)) {
                                    price = pmi.RemovePlayerPrice;
                                    final double balance = PlotMe.economy.getBalance(p);
                                    if (balance < price) {
                                        this.Send(p, this.RED + this.C("MsgNotEnoughRemove") + " " + this.C("WordMissing") + " " + this.RESET + this.f(price - balance, false));
                                        return true;
                                    }
                                    final EconomyResponse er = PlotMe.economy.withdrawPlayer(p, price);
                                    if (!er.transactionSuccess()) {
                                        this.Send(p, this.RED + er.errorMessage);
                                        this.warn(er.errorMessage);
                                        return true;
                                    }
                                }
                                if (allowed.startsWith("group:")) {
                                    plot.removeAllowedGroup(allowed);
                                }
                                else {
                                    plot.removeAllowed(allowed);
                                }
                                this.Send(p, this.C("WordPlayer") + " " + this.RED + allowed + this.RESET + " " + this.C("WordRemoved") + ". " + this.f(-price));
                                if (this.isAdv) {
                                    PlotMe.logger.info(this.LOG + p.getName() + " " + this.C("MsgRemovedPlayer") + " " + allowed + " " + this.C("MsgFromPlot") + " " + id + ((price != 0.0) ? (" " + this.C("WordFor") + " " + price) : ""));
                                }
                            }
                            else {
                                this.Send(p, this.C("WordPlayer") + " " + this.RED + args[1] + this.RESET + " " + this.C("MsgWasNotAllowed"));
                            }
                        }
                        else {
                            this.Send(p, this.RED + this.C("MsgThisPlot") + "(" + id + ") " + this.C("MsgNotYoursNotAllowedRemove"));
                        }
                    }
                }
                else {
                    this.Send(p, this.RED + this.C("MsgThisPlot") + "(" + id + ") " + this.C("MsgHasNoOwner"));
                }
            }
        }
        else {
            this.Send(p, this.RED + this.C("MsgPermissionDenied"));
        }
        return true;
    }
    
    private boolean undeny(final Player p, final String[] args) {
        if (PlotMe.cPerms(p, "PlotMe.admin.undeny") || PlotMe.cPerms(p, "PlotMe.use.undeny")) {
            if (!PlotManager.isPlotWorld(p)) {
                this.Send(p, this.RED + this.C("MsgNotPlotWorld"));
            }
            else {
                final String id = PlotManager.getPlotId(p.getLocation());
                if (id.equals("")) {
                    this.Send(p, this.RED + this.C("MsgNoPlotFound"));
                }
                else if (!PlotManager.isPlotAvailable(id, p)) {
                    if (args.length < 2 || args[1].equalsIgnoreCase("")) {
                        this.Send(p, this.C("WordUsage") + ": " + this.RED + "/plotme " + this.C("CommandUndeny") + " <" + this.C("WordPlayer") + ">");
                    }
                    else {
                        final Plot plot = PlotManager.getPlotById(p, id);
                        final String playername = p.getName();
                        final UUID playeruuid = p.getUniqueId();
                        final String denied = args[1];
                        if (plot.ownerId.equals(playeruuid) || PlotMe.cPerms(p, "PlotMe.admin.undeny")) {
                            if (plot.isDeniedConsulting(denied) || plot.isGroupDenied(denied)) {
                                final World w = p.getWorld();
                                final PlotMapInfo pmi = PlotManager.getMap(w);
                                double price = 0.0;
                                if (PlotManager.isEconomyEnabled(w)) {
                                    price = pmi.UndenyPlayerPrice;
                                    final double balance = PlotMe.economy.getBalance(p);
                                    if (balance < price) {
                                        this.Send(p, this.RED + this.C("MsgNotEnoughUndeny") + " " + this.C("WordMissing") + " " + this.RESET + this.f(price - balance, false));
                                        return true;
                                    }
                                    final EconomyResponse er = PlotMe.economy.withdrawPlayer(p, price);
                                    if (!er.transactionSuccess()) {
                                        this.Send(p, this.RED + er.errorMessage);
                                        this.warn(er.errorMessage);
                                        return true;
                                    }
                                }
                                if (denied.startsWith("group:")) {
                                    plot.removeDeniedGroup(denied);
                                }
                                else {
                                    plot.removeDenied(denied);
                                }
                                this.Send(p, this.C("WordPlayer") + " " + this.RED + denied + this.RESET + " " + this.C("MsgNowUndenied") + " " + this.f(-price));
                                if (this.isAdv) {
                                    PlotMe.logger.info(this.LOG + playername + " " + this.C("MsgUndeniedPlayer") + " " + denied + " " + this.C("MsgFromPlot") + " " + id + ((price != 0.0) ? (" " + this.C("WordFor") + " " + price) : ""));
                                }
                            }
                            else {
                                this.Send(p, this.C("WordPlayer") + " " + this.RED + args[1] + this.RESET + " " + this.C("MsgWasNotDenied"));
                            }
                        }
                        else {
                            this.Send(p, this.RED + this.C("MsgThisPlot") + "(" + id + ") " + this.C("MsgNotYoursNotAllowedUndeny"));
                        }
                    }
                }
                else {
                    this.Send(p, this.RED + this.C("MsgThisPlot") + "(" + id + ") " + this.C("MsgHasNoOwner"));
                }
            }
        }
        else {
            this.Send(p, this.RED + this.C("MsgPermissionDenied"));
        }
        return true;
    }
    
    private boolean setowner(final Player p, final String[] args) {
        if (PlotMe.cPerms(p, "PlotMe.admin.setowner")) {
            if (!PlotManager.isPlotWorld(p)) {
                this.Send(p, this.RED + this.C("MsgNotPlotWorld"));
            }
            else {
                final String id = PlotManager.getPlotId(p.getLocation());
                if (id.equals("")) {
                    this.Send(p, this.RED + this.C("MsgNoPlotFound"));
                }
                else if (args.length < 2 || args[1].equals("")) {
                    this.Send(p, this.C("WordUsage") + ": " + this.RED + "/plotme " + this.C("CommandSetowner") + " <" + this.C("WordPlayer") + ">");
                }
                else {
                    final String newowner = args[1];
                    String oldowner = "<" + this.C("WordNotApplicable") + ">";
                    final String playername = p.getName();
                    if (!PlotManager.isPlotAvailable(id, p)) {
                        final Plot plot = PlotManager.getPlotById(p, id);
                        final PlotMapInfo pmi = PlotManager.getMap(p);
                        oldowner = plot.owner;
                        final OfflinePlayer playeroldowner = Bukkit.getOfflinePlayer(plot.ownerId);
                        if (PlotManager.isEconomyEnabled(p)) {
                            if (pmi.RefundClaimPriceOnSetOwner && !newowner.equals(oldowner)) {
                                final EconomyResponse er = PlotMe.economy.depositPlayer(playeroldowner, pmi.ClaimPrice);
                                if (!er.transactionSuccess()) {
                                    this.Send(p, this.RED + er.errorMessage);
                                    this.warn(er.errorMessage);
                                    return true;
                                }
                                for (final Player player : Bukkit.getServer().getOnlinePlayers()) {
                                    if (player.getName().equalsIgnoreCase(oldowner)) {
                                        this.Send(player, this.C("MsgYourPlot") + " " + id + " " + this.C("MsgNowOwnedBy") + " " + newowner + ". " + this.f(pmi.ClaimPrice));
                                        break;
                                    }
                                }
                            }
                            if (plot.currentbidder != null && !plot.currentbidder.equals("")) {
                                final OfflinePlayer playercurrentbidder = Bukkit.getOfflinePlayer(plot.currentbidderId);
                                final EconomyResponse er2 = PlotMe.economy.depositPlayer(playercurrentbidder, plot.currentbid);
                                if (!er2.transactionSuccess()) {
                                    this.Send(p, er2.errorMessage);
                                    this.warn(er2.errorMessage);
                                }
                                else {
                                    for (final Player player2 : Bukkit.getServer().getOnlinePlayers()) {
                                        if (player2.getName().equalsIgnoreCase(plot.currentbidder)) {
                                            this.Send(player2, this.C("WordPlot") + " " + id + " " + this.C("MsgChangedOwnerFrom") + " " + oldowner + " " + this.C("WordTo") + " " + newowner + ". " + this.f(plot.currentbid));
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                        plot.currentbidder = "";
                        plot.currentbidderId = null;
                        plot.currentbid = 0.0;
                        plot.auctionned = false;
                        plot.forsale = false;
                        PlotManager.setSellSign(p.getWorld(), plot);
                        plot.updateField("currentbidder", "");
                        plot.updateField("currentbid", 0);
                        plot.updateField("auctionned", false);
                        plot.updateField("forsale", false);
                        plot.updateField("currentbidderid", null);
                        plot.owner = newowner;
                        PlotManager.setOwnerSign(p.getWorld(), plot);
                        plot.updateField("owner", newowner);
                    }
                    else {
                        PlotManager.createPlot(p.getWorld(), id, newowner, null);
                    }
                    this.Send(p, this.C("MsgOwnerChangedTo") + " " + this.RED + newowner);
                    if (this.isAdv) {
                        PlotMe.logger.info(this.LOG + playername + " " + this.C("MsgChangedOwnerOf") + " " + id + " " + this.C("WordFrom") + " " + oldowner + " " + this.C("WordTo") + " " + newowner);
                    }
                }
            }
        }
        else {
            this.Send(p, this.RED + this.C("MsgPermissionDenied"));
        }
        return true;
    }
    
    private boolean id(final Player p, final String[] args) {
        if (PlotMe.cPerms(p, "PlotMe.admin.id")) {
            if (!PlotManager.isPlotWorld(p)) {
                this.Send(p, this.RED + this.C("MsgNotPlotWorld"));
            }
            else {
                final String plotid = PlotManager.getPlotId(p.getLocation());
                if (plotid.equals("")) {
                    this.Send(p, this.RED + this.C("MsgNoPlotFound"));
                }
                else {
                    p.sendMessage(this.BLUE + this.C("WordPlot") + " " + this.C("WordId") + ": " + this.RESET + plotid);
                    final Location bottom = PlotManager.getPlotBottomLoc(p.getWorld(), plotid);
                    final Location top = PlotManager.getPlotTopLoc(p.getWorld(), plotid);
                    p.sendMessage(this.BLUE + this.C("WordBottom") + ": " + this.RESET + bottom.getBlockX() + ChatColor.BLUE + "," + this.RESET + bottom.getBlockZ());
                    p.sendMessage(this.BLUE + this.C("WordTop") + ": " + this.RESET + top.getBlockX() + ChatColor.BLUE + "," + this.RESET + top.getBlockZ());
                }
            }
        }
        else {
            this.Send(p, this.RED + this.C("MsgPermissionDenied"));
        }
        return true;
    }
    
    private boolean move(final Player p, final String[] args) {
        if (PlotMe.cPerms(p, "PlotMe.admin.move")) {
            if (!PlotManager.isPlotWorld(p)) {
                this.Send(p, this.RED + this.C("MsgNotPlotWorld"));
            }
            else if (args.length < 3 || args[1].equalsIgnoreCase("") || args[2].equalsIgnoreCase("")) {
                this.Send(p, this.C("WordUsage") + ": " + this.RED + "/plotme " + this.C("CommandMove") + " <" + this.C("WordIdFrom") + "> <" + this.C("WordIdTo") + "> " + this.RESET + this.C("WordExample") + ": " + this.RED + "/plotme " + this.C("CommandMove") + " 0;1 2;-1");
            }
            else {
                final String plot1 = args[1];
                final String plot2 = args[2];
                if (!PlotManager.isValidId(plot1) || !PlotManager.isValidId(plot2)) {
                    this.Send(p, this.C("WordUsage") + ": " + this.RED + "/plotme " + this.C("CommandMove") + " <" + this.C("WordIdFrom") + "> <" + this.C("WordIdTo") + "> " + this.RESET + this.C("WordExample") + ": " + this.RED + "/plotme " + this.C("CommandMove") + " 0;1 2;-1");
                    return true;
                }
                if (PlotManager.movePlot(p.getWorld(), plot1, plot2)) {
                    this.Send(p, this.C("MsgPlotMovedSuccess"));
                    if (this.isAdv) {
                        PlotMe.logger.info(this.LOG + p.getName() + " " + this.C("MsgExchangedPlot") + " " + plot1 + " " + this.C("MsgAndPlot") + " " + plot2);
                    }
                }
                else {
                    this.Send(p, this.RED + this.C("ErrMovingPlot"));
                }
            }
        }
        else {
            this.Send(p, this.RED + this.C("MsgPermissionDenied"));
        }
        return true;
    }
    
    private boolean reload(final CommandSender s, final String[] args) {
        if (!(s instanceof Player) || PlotMe.cPerms(s, "PlotMe.admin.reload")) {
            this.plugin.initialize();
            this.Send(s, this.C("MsgReloadedSuccess"));
            if (this.isAdv) {
                PlotMe.logger.info(this.LOG + s.getName() + " " + this.C("MsgReloadedConfigurations"));
            }
        }
        else {
            this.Send(s, this.RED + this.C("MsgPermissionDenied"));
        }
        return true;
    }
    
    private StringBuilder whitespace(final int length) {
        final int spaceWidth = MinecraftFontWidthCalculator.getStringWidth(" ");
        final StringBuilder ret = new StringBuilder();
        for (int i = 0; i + spaceWidth < length; i += spaceWidth) {
            ret.append(" ");
        }
        return ret;
    }
    
    private String round(final double money) {
        return (money % 1.0 == 0.0) ? "" + Math.round(money) : "" + money;
    }
    
    private void warn(final String msg) {
        PlotMe.logger.warning(this.LOG + msg);
    }
    
    private String f(final double price) {
        return this.f(price, true);
    }
    
    private String f(final double price, final boolean showsign) {
        if (price == 0.0) {
            return "";
        }
        String format = this.round(Math.abs(price));
        if (PlotMe.economy != null) {
            format = ((price <= 1.0 && price >= -1.0) ? (format + " " + PlotMe.economy.currencyNameSingular()) : (format + " " + PlotMe.economy.currencyNamePlural()));
        }
        if (showsign) {
            return this.GREEN + ((price > 0.0) ? ("+" + format) : ("-" + format));
        }
        return this.GREEN + format;
    }
    
    private void Send(final CommandSender cs, final String text) {
        cs.sendMessage(this.PREFIX + text);
    }
    
    private String FormatBiome(String biome) {
        biome = biome.toLowerCase();
        final String[] tokens = biome.split("_");
        StringBuilder biomeBuilder = new StringBuilder();
        for (String token : tokens) {
            token = token.substring(0, 1).toUpperCase() + token.substring(1);
            if (biomeBuilder.toString().equalsIgnoreCase("")) {
                biomeBuilder = new StringBuilder(token);
            }
            else {
                biomeBuilder.append("_").append(token);
            }
        }
        biome = biomeBuilder.toString();
        return biome;
    }
}

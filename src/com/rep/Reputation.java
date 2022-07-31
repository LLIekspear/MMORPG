package com.rep;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import com.data.PlayerConfigurator;

//TODO в табе отображать ранг, можно ещё отображать в чате и над башкой
public class Reputation {

	public static String getStringRank(Player p) {
		int rep=PlayerConfigurator.getHeroRep(p);
		if(rep<750)
			return "6-го ранга";
		else if(rep>=750&&rep<2850)
			return "5-го ранга";
		else if(rep>=2850&&rep<4450)
			return "4-го ранга";
		else if(rep>=4450&&rep<5950)
			return "3-го ранга";
		else if(rep>=5950&&rep<7150)
			return "2-го ранга";
		else if(rep>=7150&&rep<8000)
			return "1-го ранга";
		else if(rep>=8000)
			return "героя";
		return "ноунейма";
	}
	
	public static String getStringRank(OfflinePlayer p) {
		int rep=PlayerConfigurator.getHeroRep(p);
		if(rep<750)
			return "6-го ранга";
		else if(rep>=750&&rep<2850)
			return "5-го ранга";
		else if(rep>=2850&&rep<4450)
			return "4-го ранга";
		else if(rep>=4450&&rep<5950)
			return "3-го ранга";
		else if(rep>=5950&&rep<7150)
			return "2-го ранга";
		else if(rep>=7150&&rep<8000)
			return "1-го ранга";
		else if(rep>=8000)
			return "героя";
		return "ноунейма";
	}
	
	public static int getKillReward(int repKiller, int repKilled) {
		//for 6 rank
		if(repKiller<750&&repKilled<750)
			return 10;
		else if(repKiller<750&&repKilled>750)
			return (int)(10+(0.1*25));
		else if(repKiller>750&&repKilled<750)
			return (int)(10-(0.1*25));
		//for 5 rank
		else if(repKiller<2850&&repKilled<2850)
			return 20;
		else if(repKiller<2850&&repKilled>2850)
			return (int)(20+(0.2*25));
		else if(repKiller>2850&&repKilled<2850)
			return (int)(20-(0.2*25));
		//for 4 rank
		else if(repKiller<4450&&repKilled<4450)
			return 35;
		else if(repKiller<4450&&repKilled>4450)
			return (int)(35+(0.35*25));
		else if(repKiller>4450&&repKilled<4450)
			return (int)(35-(0.35*25));
		//for 3 rank
		else if(repKiller<5950&&repKilled<5950)
			return 40;
		else if(repKiller<5950&&repKilled>5950)
			return (int)(40+(0.4*25));
		else if(repKiller>5950&&repKilled<5950)
			return (int)(40-(0.4*25));
		//for 2 rank
		else if(repKiller<7150&&repKilled<7150)
			return 50;
		else if(repKiller<7150&&repKilled>7150)
			return (int)(50+(0.5*25));
		else if(repKiller>7150&&repKilled<7150)
			return (int)(50-(0.5*25));
		//for 1 rank
		else if(repKiller<8000&&repKilled<8000)
			return 60;
		else if(repKiller<8000&&repKilled>8000)
			return (int)(60+(0.6*25));
		else if(repKiller>8000&&repKilled<8000)
			return (int)(60-(0.6*25));
		//for hero
		else if(repKiller>8000&&repKilled>8000)
			return 80;
		return 10;
	}
}

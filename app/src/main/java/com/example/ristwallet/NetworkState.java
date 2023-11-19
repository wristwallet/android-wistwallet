package com.example.ristwallet;

import android.util.Pair;

import java.util.HashMap;
import java.util.Map;

import kotlin.Triple;

public class NetworkState {
    private static NetworkState networkStateInstance = null;

    private final Map<String, Triple<String, String, Integer>> addressDictionary = new HashMap<String, Triple<String, String, Integer>>() {
        {
//            put("Ethereum", new Triple<>("https://eth-sepolia.g.alchemy.com/v2/taN5ntVJsi4Pk_w8ZxCBVcc_So3bZUPC", "ETH", R.drawable.ethereum));
            put("Ethereum", new Triple<>("https://mainnet.infura.io/v3/b30f49759769470c8acac821910d824f", "ETH", R.drawable.ethereum));
            put("Arbitrum", new Triple<>("https://arb1.arbitrum.io/rpc", "ETH", R.drawable.arbitrum));
            put("Base", new Triple<> ("https://mainnet.base.org/", "ETH", R.drawable.base));
            put("Celo", new Triple<>("https://forno.celo.org/", "CELO", R.drawable.celo));
            put("GnosisChain", new Triple<>("https://rpc.gnosis.gateway.fm/", "xDAI", R.drawable.gnosis));
            put("Mantle", new Triple<>("https://mantle-mainnet.public.blastapi.io/", "MNT", R.drawable.manal));
            put("Polygon", new Triple<>("https://polygon-rpc.com/", "MATIC", R.drawable.polygon));
            put("Scroll", new Triple<>("https://rpc.scroll.io/", "ETH", R.drawable.scroll));
            put("Starknet", new Triple<>("https://starknet-mainnet.g.alchemy.com/v2/demo", "ETH", R.drawable.starknet));
            put("zkSync", new Triple<> ("https://mainnet.era.zksync.io/", "ETH", R.drawable.zksync));
        }
    };

    String currentNetwork;
    String currentEndpoint;
    String currentCurrency;
    Integer currentLogo;


    private NetworkState() {
        currentNetwork = "Ethereum";
        currentEndpoint = addressDictionary.get(currentNetwork).getFirst();
        currentCurrency = addressDictionary.get(currentNetwork).getSecond();
        currentLogo = addressDictionary.get(currentNetwork).getThird();
    }

    public static NetworkState getNetwork() {
        if (networkStateInstance == null) {
            networkStateInstance = new NetworkState();
        }
        return networkStateInstance;
    }

    public void changeNetwork(String networkName) {
        this.currentNetwork = networkName;
        this.currentEndpoint = addressDictionary.get(currentNetwork).getFirst();
        this.currentCurrency = addressDictionary.get(currentNetwork).getSecond();
        this.currentLogo = addressDictionary.get(currentNetwork).getThird();
    }
}

package com.example.ristwallet;

import android.util.Pair;

import java.util.HashMap;
import java.util.Map;

public class NetworkState {
    private static NetworkState networkStateInstance = null;

    private final Map<String, Pair<String, String>> addressDictionary = new HashMap<String, Pair<String, String>>() {
        {
            put("Ethereum", new Pair<>("https://eth-sepolia.g.alchemy.com/v2/taN5ntVJsi4Pk_w8ZxCBVcc_So3bZUPC", "ETH"));
            put("GnosisChain", new Pair<>("https://rpc.gnosis.gateway.fm/", "xDAI"));
            put("Arbitrum", new Pair<>("https://arb1.arbitrum.io/rpc", "ETH"));
            put("Polygon", new Pair<>("https://polygon-rpc.com/", "MATIC"));
            put("Mantle", new Pair<>("https://mantle-mainnet.public.blastapi.io/", "ETH"));
            put("zkSync", new Pair<> ("https://mainnet.era.zksync.io/", "ETH"));
            put("Starknet", new Pair<>("https://starknet-mainnet.g.alchemy.com/v2/demo", "ETH"));
            put("Scroll", new Pair<>("https://rpc.scroll.io/", "ETH"));
            put("Celo", new Pair<>("https://forno.celo.org/", "CELO"));
            put("Base", new Pair<> ("https://mainnet.base.org/", "ETH"));
        }
    };

    String currentNetwork;
    String currentEndpoint;
    String currentCurrency;


    private NetworkState() {
        currentNetwork = "Ethereum";
        currentEndpoint = addressDictionary.get(currentNetwork).first;
        currentCurrency = addressDictionary.get(currentNetwork).second;
    }

    public static NetworkState getNetwork() {
        if (networkStateInstance == null) {
            networkStateInstance = new NetworkState();
        }
        return networkStateInstance;
    }

    public void changeNetwork(String networkName) {
        this.currentNetwork = networkName;
        this.currentEndpoint = addressDictionary.get(currentNetwork).first;
        this.currentCurrency = addressDictionary.get(currentNetwork).second;
    }
}

package com.fsh.demo.websocket;

public enum MessageType {

    /* status updates */
    PING,

    /* commands */
    SUBSCRIBE_PRODUCT,
    UNSUBSCRIBE_PRODUCT,
    /* command replies */

    /* events */
    MARKET_PRICE,
    MARKET_DEPTH,
    ORDER,
    TRADE,
    OWN_ORDERS,
    OWN_TRADE;

    private String url;

    MessageType() {
        url = "";
    }

    public String getUrl() {
        return url;
    }
}

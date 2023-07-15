package com.ms.msuser.RouteValidator;

public enum CandidatRoute {

    /* URLusers */
    USER_UPDATE("/user/Update"),
    USER_BYID("/user"),
    USER_NEW_PASSWORD("/user/NewPassword"),
    /* URLOffre */
    OFFER_LIST("/offerList"),
	OFFER_PAGINATION("/pagination"),
	OFFER_NOTEXPIRED("/getOfferNotEpired"),
	OFFER_GETFOFFER("/getOfferById"),
    OFFER_NOTEXPIRED_PAGINATION("/getOfferNotEpiredPagination"),
    /* URLApplication*/
    APPLICATION_ByUSERID("/applicationByUserId"),
    APPLICATION_BYUSER_PAGINATED("/applicationByUserIdPaginated"),
    APPLICATION_BYUSERID_BYOFFERID("/applicationByOfferidAndUserId"),
    DELETE_APPLICATION("/deleteApplication"),
    FILEID_BYUSER("/fileByUserId"),
    UPDATE_CV("/updateContent"),
    /* URLPostuler*/
    UPLOAD_CV("/upload"),
    CALCUL_SCORE("/score"),
    RECALCUL_SCORE("/reCalcul"),
    POSTULER("/updateApplication"),
	/* URLCalculator*/
	APPLICATION_ByIDOFFER("/applicationByOfferid");
	

    private final String route;

    CandidatRoute(String route) {
        this.route = route;
    }

    public String getRoute() {
        return route;
    }

}

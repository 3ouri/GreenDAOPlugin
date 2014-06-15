/**
 * 
 */

var greenDAOPlugin = {
	addToDB : function(productObject, successCallback, errorCallback) {
		cordova.exec(successCallback, // success callback function
		errorCallback, // error callback function
		'GreenDAOPlugin', // name of the native java class
		'addToDB', // name of the action to performed
		[ {
			"product" : productObject
		} ] // and this array of custom arguments to
		);
	}
};
module.exports = greenDAOPlugin;

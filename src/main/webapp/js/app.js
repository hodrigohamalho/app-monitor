(function(){
	var app = angular.module('app-monitor', []);

	app.controller('AppController', [ '$http', function($http){
		var self = this;
		
		$http.get('api/servers').success(function(data){
			console.log("data: "+data);
			self.servers = data;
		}).error(function(error){
			console.log("Error: "+error);
		});
		
	}]);

})();
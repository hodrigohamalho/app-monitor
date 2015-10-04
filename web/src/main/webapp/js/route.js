(function(){
	var app = angular.module('app-monitor');
	
	app.config(function($routeProvider) {
					$routeProvider.
					when('/home', {
						templateUrl: 'home.html',
						controller: 'ServerController'
					}).
					when('/about', {
						templateUrl: 'about.html'
					}).
					when('/contact', {
						templateUrl: 'contact.html'
					}).
					otherwise({
						redirectTo: '/home'
					});
				});
})();
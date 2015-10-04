(function(){
	var app = angular.module('app-monitor');
	var api = "http://localhost:8180/app-monitor-api/api/";
	
	app.controller('AppModalCtrl', function ($scope, $http, $modalInstance, app, $log) {

		$scope.app = app;

		$scope.save = function(){
			$modalInstance.close($scope.app);
		};

		$scope.cancel = function () {
//			$log.info("Cancel");
			$modalInstance.dismiss('cancel');
		};
		  
	});
	
	app.controller('ServerModalCtrl', function ($scope, $http, $modalInstance, server, $log) {

		$scope.server = server;

		$scope.save = function(){
			$modalInstance.close($scope.server);
		};

		$scope.cancel = function () {
//			$log.info("Cancel");
			$modalInstance.dismiss('cancel');
		};
		  
	});

	app.controller('ServerController', function($scope, $http, $modal, $log){
		var self = this;
		$scope.server = {};
		$scope.app = {};

		$scope.list = function(){
			$http.get(api + 'servers').then(function(serversList){
				$scope.servers = serversList.data;
			});
		};

		$scope.remove = function(server){
			var confirm = window.confirm('Are you sure to delete '+ server.dns+ '?');
			if(confirm){
				$http.delete(api + 'servers/'+server.id).then(function(){
					$scope.list();
				});
			}
		};

		$scope.serverModal = function (server) {
			if (server != null){
				$scope.server = server;
			}
				
		    var modalInstance = $modal.open({
		      animation: true,
		      templateUrl: 'serverModalForm.html',
		      controller: 'ServerModalCtrl',
		      resolve: {
		          server: function () {
		            return $scope.server;
		          }
		        }
		    });

			modalInstance.result.then(function (data) {
				$log.info("Callback modal: "+data);
				
				if ($scope.server.id != null){
					// UPDATE
					$http.put(api + 'servers', $scope.server).then(function(){
						$scope.server = {};
						$scope.list();
					});
				}else{
					// SAVE
					$http.post(api + 'servers', $scope.server).then(function(){
						$scope.server = {};
						$scope.list();
					});
				}
			}, function () {
				// When modal is canceled
				$log.info("Modal canceled: ");
				$scope.server = {};
			});
		};
		
		$scope.appModal = function (app, serverId) {
			if (app != null){
				$scope.app = app;
			}
			$scope.app.server = { "id": serverId };
				
		    var modalInstance = $modal.open({
		      animation: true,
		      templateUrl: 'appModalForm.html',
		      controller: 'AppModalCtrl',
		      resolve: {
		          app: function () {
		            return $scope.app;
		          }
		        }
		    });

			modalInstance.result.then(function (data) {
				if ($scope.app.id != null){
					// UPDATE
					$http.put(api +'apps', $scope.app).then(function(){
						$scope.app = {};
						$scope.list();
					});
				}else{
					// SAVE
					$http.post(api +'apps', $scope.app).then(function(){
						$scope.app = {};
						$scope.list();
					});
				}
			}, function () {
				$scope.app = {};
			});
		};
		
		$scope.removeApp = function(app){
			var confirm = window.confirm('Are you sure to delete '+ app.context+ '?');
			if(confirm){
				$http.delete(api + 'apps/'+app.id).then(function(){
					$scope.list();
				});
			}
		};

		$scope.list();

	});

})();
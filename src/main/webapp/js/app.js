(function(){
	var app = angular.module('app-monitor', ['ui.bootstrap']);
	
	app.controller('ServerModalCtrl', function ($scope, $http, $modalInstance, server, $log) {

		$scope.server = server;

		$scope.save = function(){
			if ($scope.server.id != null){
				// UPDATE
				$http.put('api/servers', $scope.server).then(function(){
					$log.info("server updated!");
				});
			}else{
				// SAVE
				$http.post('api/servers', $scope.server).then(function(){
					$log.info("server saved!");
				});
			}
			
			$modalInstance.close($scope.server);
		};

		$scope.cancel = function () {
			$log.info("Cancel");
			$modalInstance.dismiss('cancel');
		};
		  
	});

	app.controller('ServerController', function($scope, $http, $modal, $log){
		var self = this;
		$scope.server = {};

		$scope.list = function(){
			$http.get('api/servers').then(function(serversList){
				$scope.servers = serversList.data;
			});
		};

		$scope.remove = function(server){
			var confirm = window.confirm('Tem certeza que deseja excluir o server '+ server.dns+ '?');
			if(confirm){
				$http.delete('api/servers/'+server.id).then(function(){
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
				$scope.list();
				$log.info("Callback modal: "+data);
			}, function () {
				$log.info('Modal dismissed at: ' + new Date());
			});
		};

		$scope.list();

	});

})();
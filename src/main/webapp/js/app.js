(function(){
	var app = angular.module('app-monitor', ['ui.bootstrap']);

	app.controller('ServerController', [ '$scope', '$http', function($scope, $http){
		var self = this;
		$scope.server = {};

		$scope.list = function(){
			$http.get('api/servers').then(function(serversList){
				$scope.servers = serversList.data;
			});
		};

		$scope.save = function(){
			if ($scope.server.id != null){
				$http.put('api/servers', $scope.server).then(function(){
					$scope.server = {};
				}, function(response){
					console.log("ERROR on update server: "+response);
					$scope.list();
				});
			}else{
				$http.post('api/servers', $scope.server).then(function(){
					$scope.servers.unshift($scope.server);
					$scope.server = {};
				});
			}
		};

		$scope.remove = function(server){
			var confirm = window.confirm('Tem certeza que deseja excluir o server '+ server.dns+ '?');
			if(confirm){
				$http.delete('api/servers/'+server.id).then(function(){
					$scope.list();
				});
			}
		};

		$scope.edit = function(server){
			$scope.server = server;
		};

		$scope.list();

	}]);

})();
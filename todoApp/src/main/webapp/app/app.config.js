'use strict';

angular.module('todoApp').config(
	[
		'$stateProvider',
		'$urlRouterProvider',
		'localStorageServiceProvider',
		'ChartJsProvider',
		function config($stateProvider, $urlRouterProvider,
			localStorageServiceProvider, ChartJsProvider) {
			
			// Configure all bar charts
			ChartJsProvider.setOptions('bar', {
				scales: {
					yAxes: [{
						ticks: {
							beginAtZero:true
						}
					}]
				},
				legend: { display: true }
			  });
			
			// Configure all pie charts
			ChartJsProvider.setOptions('pie', {				
				legend: { display: true }
			  });

			localStorageServiceProvider.setPrefix('todoApp')
				.setStorageType('localStorage');

			$stateProvider.state('login', {
					url: '/login',
					template: '<login></login>'
				}).state('register', {
					url: '/register',
					template: '<register></register>'
				}).state('forgotpassword', {
					url: '/forgotpassword',
					template: '<forgotpassword></forgotpassword>'
				}).state('resetpassword', {
					url: '/resetpassword/:token',
					template: '<resetpassword></resetpassword>'
				}).state('dummypage', {
					url: '/dummypage/:token',
					template: '<dummypage></dummypage>'
				})
				.state('homepage', {
					url: '/homepage',
					template: '<homepage></homepage>'
					/* templateUrl: 'app/homepage/homepage.template.html',
					controller: 'homepageController' */
				})
				.state('admindashboard', {
					url: '/admindashboard',
					template: '<admindashboard></admindashboard>'
				});
			$urlRouterProvider.otherwise('/login');
		}
	]);
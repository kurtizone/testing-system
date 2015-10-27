angular
    .module(
    'adminModule',
    ['spring-security-csrf-token-interceptor', 'ui.bootstrap',
        'ui.router', 'ui.bootstrap.showErrors', 'ngTable',
        'pascalprecht.translate', 'ngCookies', 'ui.select', 'ngSanitize', 'localytics.directives', 'checklist-model','ngAnimate', 'toaster',
        'angular-loading-bar'])

    .config(
    [
        '$translateProvider',
        '$stateProvider',
        '$urlRouterProvider',
        'showErrorsConfigProvider',
        'cfpLoadingBarProvider',
        '$provide',
        function ($translateProvider, $stateProvider,
                  $urlRouterProvider, showErrorsConfigProvider,cfpLoadingBarProvider, $provide) {
            showErrorsConfigProvider.showSuccess(true);
            cfpLoadingBarProvider.includeSpinner = false;
            cfpLoadingBarProvider.latencyThreshold = 500;

            /**
             * i18n configuration.
             */
            $translateProvider.useStaticFilesLoader({
                prefix: '/resources/assets/i18n/welcome-',
                suffix: '.json'
            });
            $translateProvider.useLocalStorage();
            $translateProvider
                .useSanitizeValueStrategy('escaped');
            $translateProvider.preferredLanguage('ukr');
            /**
             * Routing configuration.
             */
            $urlRouterProvider.otherwise('/');
            $stateProvider
                .state(
                'main',
                {
                    url: '/',
                    templateUrl: '/resources/app/admin/views/main-panel.html'
                })
                .state(
                'subjects',
                {
                    url: '/lecturers',
                    templateUrl: '/resources/app/admin/views/subjects-panel.html'
                })
                .state(
                'lecturers',
                {
                    url: '/lecturers',
                    templateUrl: '/resources/app/admin/views/lecturers-panel.html'
                })
                .state(
                'groups',
                {
                    url: '/groups',
                    templateUrl: '/resources/app/admin/views/groups-panel.html'
                })
                .state(
                'students',
                {
                    url: '/students',
                    templateUrl: '/resources/app/admin/views/students-panel.html'
                })
                .state(
                'results',
                {
                    url: '/results',
                    templateUrl: '/resources/app/admin/views/results-panel.html'
                })

            /*
             Extended ui-select-choices: added watch for ng-translate event called translateChangeEnd
             When translation of page will end, items of select (on the scope) will be changed too.
             Then we refresh the items of select to get them from scope.
             */
            $provide.decorator('uiSelectDirective', function( $delegate, $parse, $injector) {
                var some_directive = $delegate[ 0],
                    preCompile = some_directive.compile;

                some_directive.compile = function compile() {
                    var link = preCompile.apply( this, arguments );

                    return function( scope, element, attrs, controller ) {
                        link.apply( this, arguments );

                        var $select = controller[ 0 ];

                        var rootScope= $injector.get('$rootScope');

                        rootScope.$on('$translateChangeEnd', function(event){
                            scope.setTypeDataLanguage();
                            $select.refreshItems();
                        });

                    };
                };

                return $delegate;
            });
        }]);

angular.module('adminModule').run(function (paginationConfig) {
    paginationConfig.firstText = 'Перша';
    paginationConfig.previousText = 'Попередня';
    paginationConfig.nextText = 'Наступна';
    paginationConfig.lastText = 'Остання';
});

define(['controllers/TopNavBarController', 'controllers/MainPanelController',
    'controllers/SubjectsPanelController',
    'controllers/OrganizationAddModalController',
    'controllers/OrganizationEditModalController',
    'controllers/OrganizationEditHistoryModalController',
    'controllers/DeviceController',
    'controllers/CategoryDeviceAddModalController',
    'controllers/CategoryDeviceEditModalController',
    'controllers/CounterTypePanelController',
    'controllers/CounterTypeAddController',
    'controllers/CounterTypeEditController',
    'controllers/SettingsController',
    'controllers/SysAdminsController',
    'controllers/SysAdminEditModalController',
    'controllers/UsersController',
    'controllers/SysAdminAddModalController',
    'controllers/InternationalizationController',
    'controllers/agreement/AgreementController',
    'controllers/agreement/AgreementAddController',
    'services/OrganizationService', 'services/StatisticService',
    'services/UserService', 'services/AddressService',
    'services/DeviceService', 'services/DevicesService',
    'services/CounterTypeService',
    'services/AgreementService',
    'services/SettingsService',
    'services/UsersService',
    'services/RoleService',
    'directives/unique',
    'controllers/CommonController'

], function () {
});

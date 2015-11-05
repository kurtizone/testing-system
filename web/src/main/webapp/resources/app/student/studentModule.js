angular
    .module(
    'studentModule',
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
                    templateUrl: '/resources/app/student/views/main-panel.html'
                })
                .state(
                'subjects',
                {
                    url: '/subjects',
                    templateUrl: '/resources/app/student/views/subjects-panel.html'
                })
                .state(
                'lecturers',
                {
                    url: '/lecturers',
                    templateUrl: '/resources/app/student/views/lecturers-panel.html'
                })
                .state(
                'groups',
                {
                    url: '/groups',
                    templateUrl: '/resources/app/student/views/groups-panel.html'
                })
                .state(
                'students',
                {
                    url: '/students',
                    templateUrl: '/resources/app/student/views/students-panel.html'
                })
                .state(
                'results',
                {
                    url: '/results',
                    templateUrl: '/resources/app/student/views/results-panel.html'
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

angular.module('studentModule').run(function (paginationConfig) {
    paginationConfig.firstText = 'Перша';
    paginationConfig.previousText = 'Попередня';
    paginationConfig.nextText = 'Наступна';
    paginationConfig.lastText = 'Остання';
});

define(['controllers/TopNavBarController', 'controllers/MainPanelController',
    'controllers/subject/SubjectsPanelController',
    'controllers/subject/SubjectAddModalController',
    'controllers/subject/SubjectEditModalController',
    'controllers/lecturer/LecturersPanelController',
    'controllers/lecturer/LecturerAddModalController',
    'controllers/lecturer/LecturerEditModalController',
    'controllers/student/StudentsPanelController',
    'controllers/student/StudentAddModalController',
    'controllers/student/StudentEditModalController',
    'controllers/group/GroupsPanelController',
    'controllers/group/GroupAddModalController',
    'controllers/group/GroupEditModalController',
    'controllers/UsersController',
    'controllers/InternationalizationController',
    'services/StatisticService',
    'services/UserService',
    'services/SubjectsService',
    'services/LecturersService',
    'services/StudentsService',
    'services/GroupsService',
    'services/SettingsService',
    'services/RoleService',
    'directives/unique',
    'controllers/CommonController'

], function () {
});
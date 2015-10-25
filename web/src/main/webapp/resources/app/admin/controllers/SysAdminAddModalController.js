angular
    .module('adminModule')
    .controller(
    'SysAdminAddModalController',
    [
        '$rootScope',
        '$scope',
        '$log',
        '$translate',
        '$modalInstance',
        '$filter',
        '$modal',
        '$timeout',
        'AddressService',
        'OrganizationService',
        'UserService',
        function ($rootScope, $scope, $log, $translate, $modalInstance, $filter,
                  $modal, $timeout,
                  addressService, organizationService,
                  userService) {

            var employeeData = {};

            $scope.regions = null;
            $scope.districts = [];
            $scope.localities = [];
            $scope.streets = [];
            $scope.buildings = [];


            /**
             * Finds all regions
             */
            function initFormData() {
                if (!$scope.regions) {
                    addressService.findAllRegions().then(
                        function (respRegions) {
                            $log.debug($scope.sysAdminFormData);
                            $scope.regions = respRegions;

                        });
                }
            }

            initFormData();

            /**
             * Closes modal window on browser's back/forward button click.
             */

            $rootScope.$on('$locationChangeStart', function () {
                $modalInstance.close();
            });

            /**
             * Resets employee form
             */
            $scope.resetSysAdminFormData = function () {
                $scope.$broadcast('show-errors-reset');
                if ($scope.sysAdminForm) {
                    $scope.sysAdminForm.$setPristine();
                    $scope.sysAdminForm.$setUntouched();
                }
                $scope.usernameValidation = null;
                $scope.sysAdminFormData = null;
            };

            $scope.resetSysAdminFormData();

            /**
             * Validates
             */
            $scope.checkField = function (caseForValidation) {
                switch (caseForValidation) {
                    case ('firstName') :
                        var firstName = $scope.sysAdminFormData.firstName;
                        if (firstName == null) {
                        } else if ($scope.FIRST_LAST_NAME_REGEX.test(firstName)) {
                            validator('firstName', false);
                        } else {
                            validator('firstName', true);
                        }
                        break;
                    case ('lastName') :
                        var lastName = $scope.sysAdminFormData.lastName;
                        if (lastName == null) {

                        } else if ($scope.FIRST_LAST_NAME_REGEX.test(lastName)) {

                            validator('lastName', false);
                        } else {
                            validator('lastName', true);
                        }
                        break;
                    case ('middleName') :
                        var middleName = $scope.sysAdminFormData.middleName;
                        if (middleName == null) {
                        } else if ($scope.MIDDLE_NAME_REGEX.test(middleName)) {
                            validator('middleName', false);
                        } else {
                            validator('middleName', true);
                        }
                        break;
                    case ('phone') :
                        var phone = $scope.sysAdminFormData.phone;
                        if (phone == null) {
                        } else if ($scope.PHONE_REGEX.test(phone)) {
                            validator('phone', false);
                        } else {
                            validator('phone', true);
                        }
                        break;
                    case ('email') :
                        var email = $scope.sysAdminFormData.email;
                        if (email == null) {
                        } else if ($scope.EMAIL_REGEX.test(email)) {
                            validator('email', false);
                        } else {
                            validator('email', true);
                        }
                        break;
                    case ('login') :
                        var username = $scope.sysAdminFormData.username;
                        if (username == null) {
                        } else if ($scope.USERNAME_REGEX.test(username)) {
                            isUsernameAvailable(username);
                        } else {
                            validator('loginValid', false);
                        }
                        break;
                }
            };

            /**
             * Checks whereas given username is available to use
             * for new user
             * @param username
             */
            $scope.isUsernameAvailable = true;
            $scope.sysAdminFormData = {};


            function isUsernameAvailable(username) {
                userService.isUsernameAvailable(username).then(
                    function (data) {
                        validator('existLogin', data);
                    })
            }


            function validator(caseForValidation, isValid) {

                switch (caseForValidation) {
                    case 'firstName':
                        $scope.firstNameValidation = {
                            isValid: isValid,
                            css: isValid ? 'has-error' : 'has-success'
                        };
                        break;
                    case 'lastName':
                        $scope.lastNameValidation = {
                            isValid: isValid,
                            css: isValid ? 'has-error' : 'has-success'
                        };
                        break;
                    case 'middleName':
                        $scope.middleNameValidation = {
                            isValid: isValid,
                            css: isValid ? 'has-error' : 'has-success'
                        };
                        break;
                    case 'phone':
                        $scope.phoneNumberValidation = {
                            isValid: isValid,
                            css: isValid ? 'has-error' : 'has-success'
                        };
                        break;
                    case 'email':
                        $scope.emailValidation = {
                            isValid: isValid,
                            css: isValid ? 'has-error' : 'has-success'
                        };
                        break;
                    case 'loginValid':
                        $scope.usernameValidation = {
                            isValid: isValid,
                            css: isValid ? 'has-success' : 'has-error',
                            message: isValid ? undefined : 'К-сть символів не повинна бути меншою за 3\n і більшою за 16. Наприклад : vova '
                        };
                        break;
                    case 'existLogin':
                        $scope.usernameValidation = {
                            isValid: isValid,
                            css: isValid ? 'has-success' : 'has-error',
                            message: isValid ? undefined : 'Такий логін вже існує'
                        };
                        break;

                }
            }

            /**
             * Check passwords for equivalent
             */

            $scope.checkPasswords = function () {
                var first = $scope.sysAdminFormData.password;
                var second = $scope.sysAdminFormData.rePassword;
                $log.info(first);
                $log.info(second);
                var isValid = false;
                if (first != second) {
                    isValid = true;
                }
                $scope.passwordValidation = {
                    isValid: isValid,
                    css: isValid ? 'has-error' : 'has-success'
                }
            };


            /*var index = arrayObjectIndexOf($scope.regions,  $scope.user.address.region, "designation");
             $scope.sysAdminFormData.region = $scope.regions[index];
             $scope.onRegionSelected($scope.regions[index].id);*/
            initFormData();

            /**
             * Receives all possible districts.
             * On-select handler in region input form element.
             */
            $scope.receiveDistricts = function (selectedRegion) {
                if (!$scope.blockSearchFunctions) {
                    $scope.districts = [];
                    $scope.sysAdminFormData.address = {};
                    addressService.findDistrictsByRegionId(selectedRegion.id)
                        .then(function (districts) {
                            $scope.districts = districts;
                            $scope.sysAdminFormData.address.district = undefined;
                            $scope.sysAdminFormData.address.locality = undefined;
                            $scope.sysAdminFormData.address.street = "";
                        });
                }
            };

            /**
             * Receives all possible localities.
             * On-select handler in district input form element.
             */
            $scope.receiveLocalities = function (selectedDistrict) {
                if (!$scope.blockSearchFunctions) {
                    $scope.localities = [];
                    addressService.findLocalitiesByDistrictId(selectedDistrict.id)
                        .then(function (localities) {
                            console.log(localities);
                            $scope.localities = localities;
                            $scope.sysAdminFormData.address.locality = undefined;
                            $scope.sysAdminFormData.address.street = "";

                        });
                }
            };

            $scope.receiveStreets = function (selectedLocality) {
                if (!$scope.blockSearchFunctions) {
                    $scope.streets = [];
                    addressService.findStreetsByLocalityId(selectedLocality.id)
                        .then(function (streets) {
                            $scope.streets = streets;
                            $scope.sysAdminFormData.address.street = "";
                            $scope.sysAdminFormData.address.building = "";
                            $scope.sysAdminFormData.address.flat = "";
                            $log.debug("$scope.streets");
                            $log.debug($scope.streets);

                        }
                    );
                }
            };
            /**
             * Finds districts in a given region.
             * @param regionId
             *            to identify region$$
             */
            $scope.onRegionSelected = function (regionId) {
                addressService
                    .findDistrictsByRegionId(regionId)
                    .then(function (data) {
                        $scope.districts = data;
                    });
            };

            /**
             * Finds localities in a given district.
             *
             * @param districtId
             *            to identify district
             */
            $scope.onDistrictSelected = function (districtId) {
                addressService.findLocalitiesByDistrictId(
                    districtId).then(function (data) {
                        $scope.localities = data;
                    });
            };

            /**
             * Finds streets in a given locality.
             *
             * @param localityId
             *            to identify locality
             */
            $scope.onLocalitySelected = function (localityId) {
                addressService.findStreetsByLocalityId(
                    localityId).then(function (data) {
                        $scope.streets = data;
                    });
            };

            /**
             * Finds buildings in a given street.
             *
             * @param streetId
             *            to identify street
             */
            $scope.onStreetSelected = function (streetId) {
                addressService
                    .findBuildingsByStreetId(streetId)
                    .then(function (data) {
                        $scope.buildings = data;
                    });
            };

            /**
             * Convert address data to string
             * Refactor data
             */
            function retranslater() {
                $scope.sysAdminFormData.address = {
                    region: $scope.sysAdminFormData.region.designation,
                    district: $scope.sysAdminFormData.district.designation,
                    locality: $scope.sysAdminFormData.locality.designation,
                    street: $scope.sysAdminFormData.street.designation,
                    building: $scope.sysAdminFormData.building,
                    flat: $scope.sysAdminFormData.flat
                };
                sysAdmin = {
                    firstName: $scope.sysAdminFormData.firstName,
                    lastName: $scope.sysAdminFormData.lastName,
                    middleName: $scope.sysAdminFormData.middleName,
                    phone: $scope.sysAdminFormData.phone,
                    email: $scope.sysAdminFormData.email,
                    username: $scope.sysAdminFormData.username,
                    password: $scope.sysAdminFormData.password,
                    userRoles: ['SYS_ADMIN'],
                    address: $scope.sysAdminFormData.address
                };

                sysAdmin.address = {
                    region: $scope.sysAdminFormData.region.designation,
                    district: $scope.sysAdminFormData.district.designation,
                    locality: $scope.sysAdminFormData.locality.designation,
                    street: $scope.sysAdminFormData.street.designation,
                    building: $scope.sysAdminFormData.building,
                    flat: $scope.sysAdminFormData.flat
                }


            }

            var bValidation = function () {
                if (( $scope.firstNameValidation === undefined) || ($scope.lastNameValidation === undefined)
                    || ($scope.middleNameValidation === undefined) || ($scope.emailValidation === undefined)
                    || ($scope.passwordValidation === undefined) || ($scope.usernameValidation === undefined)
                    || ($scope.sysAdminFormData.region === undefined) || ($scope.sysAdminFormData.district === undefined)
                    || ($scope.sysAdminFormData.locality === undefined)
                ) {
                    $scope.incorrectValue = true;
                    return false;
                } else {
                    return true;
                }
            };

            $scope.onSysAdminFormSubmit = function () {
                $scope.$broadcast('show-errors-check-validity');
                if (bValidation()) {
                    if (!$scope.firstNameValidation.isValid && !$scope.lastNameValidation.isValid
                        && !$scope.middleNameValidation.isValid && !$scope.emailValidation.isValid) {
                        retranslater();
                        saveSysAdmin();
                    } else {
                        $scope.incorrectValue = true;
                    }
                }
            };

            /**
             * Update new employee in database.
             */
            function saveSysAdmin() {
                userService.saveUser(
                    sysAdmin).then(
                    function (data) {
                        if (data.status == 201) {
                            $rootScope.$broadcast('new-employee-added');
                            $scope.closeModal();
                            $rootScope.onTableHandling();
                            $scope.resetSysAdminFormData();

                        } else {
                            console.log(data.status);
                        }
                    });
            }

            /**
             * Receives all regex for input fields
             *
             *
             */
            $scope.FIRST_LAST_NAME_REGEX = /^([A-Z\u0410-\u042f\u0407\u0406\u0404'][a-z\u0430-\u044f\u0456\u0457\u0454']{1,20}\u002d[A-Z\u0410-\u042f\u0407\u0406\u0404'][a-z\u0430-\u044f\u0456\u0457\u0454']{1,20}|[A-Z\u0410-\u042f\u0407\u0406\u0404'][a-z\u0430-\u044f\u0456\u0457\u0454']{1,20})$/;
            $scope.MIDDLE_NAME_REGEX = /^[A-Z\u0410-\u042f\u0407\u0406\u0404'][a-z\u0430-\u044f\u0456\u0457\u0454']{1,20}$/;
            $scope.PNOHE_REGEX_MY = /^[1-9]\d{8}$/;
            $scope.PHONE_REGEX = /^[1-9]\d{8}$/;
            $scope.EMAIL_REGEX = /^[-a-z0-9~!$%^&*_=+}{'?]+(\.[-a-z0-9~!$%^&*_=+}{'?]+)*@([a-z0-9_][-a-z0-9_]*(\.[-a-z0-9_]+)*\.(aero|arpa|biz|com|coop|edu|gov|info|int|mil|museum|name|net|org|pro|travel|mobi|[a-z][a-z])|([0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}))(:[0-9]{1,5})?$/i;
            $scope.USERNAME_REGEX = /^[a-z0-9_-]{3,16}$/;
            $scope.BUILDING_REGEX = /^[1-9][0-9]{0,3}([A-Za-z]|[\u0410-\u042f\u0407\u0406\u0430-\u044f\u0456\u0457])?$/;
            $scope.FLAT_REGEX = /^([1-9][0-9]{0,3}|0)$/;
            $scope.PASSWORD_REGEX = /^(?=.{4,20}$).*/;

            /* Closes the modal window
             */
            $rootScope.closeModal = function () {
                $modalInstance.close();
            };

            //   $log.info(sysAdminFormData);

        }]);

angular.module('adminModule')
    .factory('OrganizationService', ['$http', '$log', function ($http, $log) {

        return {
            getPage: function (currentPage, itemsPerPage, search, sortCriteria, sortOrder) {
                return getDataWithParams(currentPage + '/' + itemsPerPage + '/' + sortCriteria + '/' + sortOrder, search);
            },
            saveOrganization: function (formData) {
                return $http.post("/admin/organization/add", formData)
                    .then(function (result) {
                        return result.status;

                    });
            },
            getOrganizationWithId: function (id) {
                var url = '/admin/organization/getOrganization/' + id;
                return $http.get(url).then(function (result) {
                    return result.data;
                });
            },

            editOrganization: function (formData, id) {
                var url = '/admin/organization/edit/' + id;
                return $http.post(url, formData)
                    .then(function (result) {
                        return result.status;
                    });
            },

            getOrganizationAdmin: function (id) {
                var url = '/admin/organization/getOrganizationAdmin/' + id;
                return $http.get(url).then(function (result) {
                    return result.data;
                });
            },

            getHistoryOrganizationWithId: function (id) {
                var url = '/admin/organization/edit/history/' + id;
                return $http.get(url).then(function (result) {
                    return result.data;
                });
            },
            getServiceAreaLocalities: function (organizationId) {
                return getData('/serviceArea/localities/' + organizationId);
            },
            getServiceAreaRegion: function (districtId) {
                return getData('/serviceArea/region/' + districtId);
            },
            getOrganizationByOrganizationTypeAndDeviceType: function (organizationType, deviceType) {
                return getData('getOrganization/' + organizationType + '/' + deviceType);
            }

        };
        function getDataWithParams(url, params) {
            return $http.get('/admin/organization/' + url, {
                params: params
            }).success(function (data) {
                return data;
            }).error(function (err) {
                return err;
            });
        }

        function getData(url) {
            return $http.get('/admin/organization/' + url)
                .success(function (data) {
                    return data;
                }).error(function (err) {
                    return err;
                });
        }

    }]);
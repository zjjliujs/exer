app.service("monitorService",
["$http","$resource"
, function($http, $resource){
        var self = this;

        self.getClusterTypes = function (){
            return [
                'web',
                'Cassandra',
                'ES'
            ];
        };

        self.pageStat = {
            clusterCreator:{
                show:false,
                clusterName:null,
                clusterType:null
            },
            clusterRemover: {
                show:false,
                cluster:null
            },
            nodeCreator:{
                show:false,
                cluster:null,
                node:null
            },
            nodeRemover: {
                show:false,
                node:null
            },
            nodeList:{
                cluster:null,
                order:{
                    type:null,
                    asc:false
                }
            },
            nodeDetail:{
                cluster:null,
                node:null
            },
            clusterConfig: {
                currentCluster: null
            }
        };
    }])

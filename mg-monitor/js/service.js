app.service("monitorService",
["$http","$resource"
, function($http, $resource){
        var self = this;

        self.getClusterTypes = function (){
            return [
                {name:'web集群'},
                {name:'Cassandra集群'},
                {name:'ES集群'}
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
                hostName:null,
                ip:null
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
            }
        };
    }])

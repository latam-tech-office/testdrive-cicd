oc new-project broker
oc new-app amq63-basic -p APPLICATION_NAME=broker -p MQ_TOPICS=testdrive -p MQ_USERNAME=testdrive -p MQ_PASSWORD=r3dh4t1!
oc patch dc/broker-amq --patch '{"spec":{"triggers":[{"imageChangeParams": {"automatic": true,"containerNames": ["broker-amq"],"from": {"kind": "ImageStreamTag","name": "jboss-amq-63:latest","namespace": "openshift"}},"type": "ImageChange"},{"type": "ConfigChange"}]}}'


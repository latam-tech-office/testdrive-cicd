#ansible-playbook create-msa.yaml -e package_name=com.latam.techoffice.testdrive -e project_name=customers -e directory=/Users/mauricio/projects/java/testdrive -e broker_namespace=broker -e broker_service=broker-amq -e broker_topics=testdrive
oc new-project application
oc new-app -f https://raw.githubusercontent.com/latam-tech-office/testdrive-cicd/master/ocp/template/wildfly-mongo.yaml -p APPLICATION_NAME=customers -p DB_USERNAME=testdrive -p DB_PASSWORD=r3dh4t1! -p DB_DATABASE=customers -p BROKER_ADDRESS=broker-amq-tcp.broker -p BROKER_USERNAME=testdrive -p BROKER_PASSWORD=r3dh4t1! -p BROKER_TOPICS=testdrive
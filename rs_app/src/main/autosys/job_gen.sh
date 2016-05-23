#!/bin/bash

#creates command like: sed -e s/\${ENV}/DEV/ -e s/\${MACHINE}/10.10.10.10/ SOME_TEMPLATE_JOB_FILE_
#based on property_file, executes it
#and push output to app/jils/${ENV}/SOME_TEMPLATE_JOB_FILE_${ENV}.jil
#template file cannot have any extension

if [ "$#" != 2 ]; then
	echo 'Usage: job_gen.sh [property_file] [template_file]'
	exit 0
elif ! [ -a $1 ]; then
	echo 'File: '$1' does not exist'
	exit 0
elif ! [ -a $2 ]; then
	echo 'File: '$2' does not exist'
	exit 0
fi

PROP_FILE=$1

PROPS=`cat $PROP_FILE`
ENV=""
CMD="sed "
while IFS= read -r line; 
do 	
	IFS='=' read -ra prop <<< "$line"	
	CMD+="-e s/\\$\{${prop[0]}}/${prop[1]}/ "
	
	if [[ "${prop[0]}" == "ENV" ]]; then
		ENV=${prop[1]}
	fi

done < $PROP_FILE

FILENAME=$2
FNAME=${FILENAME##*/}

mkdir -p app/jils/${ENV,,}/

touch app/jils/${ENV,,}/$FNAME$ENV.jil

CMD+=" $2 >> app/jils/${ENV,,}/$FNAME$ENV.jil"

rm -f app/jils/${ENV,,}/$FNAME$ENV.jil

#output redirected to /dev/null
#because some trash is printed 
#when sed can't find proper placeholder
eval $CMD &>/dev/null


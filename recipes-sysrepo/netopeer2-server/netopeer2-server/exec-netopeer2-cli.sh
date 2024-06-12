#!/bin/bash -

LOCAL_PATH=$(dirname $BASH_SOURCE)
echo "$LOCAL_PATH"

export LD_LIBRARY_PATH=$LOCAL_PATH/lib:$LD_LIBRARY_PATH
export LIBYANG_EXTENSIONS_PLUGINS_DIR=$LOCAL_PATH/lib/libyang/extensions/
export LIBYANG_USER_TYPES_PLUGINS_DIR=$LOCAL_PATH/lib/libyang/user_types/
$LOCAL_PATH/bin/netopeer2-cli

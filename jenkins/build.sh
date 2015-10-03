alias rsync="rsync --delete-after -azO -e '$GIT_SSH'"

upstream_ssh="55c7bb857628e1cc760000e5@rodrigoramalho-${OPENSHIFT_NAMESPACE}.rhcloud.com"

# Sync any libraries
rsync $upstream_ssh:~/.m2/ ~/.m2/

# Build/update libs and run user pre_build and build
gear build

# Run tests here

# Deploy new build

# Stop app
$GIT_SSH $upstream_ssh 'gear stop --conditional'

# Push content back to application
rsync ~/.m2/ $upstream_ssh:~/.m2/
rsync $WORKSPACE/deployments/ $upstream_ssh:'${OPENSHIFT_REPO_DIR}deployments/'
rsync $WORKSPACE/.openshift/ $upstream_ssh:'${OPENSHIFT_REPO_DIR}.openshift/'

# Configure / start app
$GIT_SSH $upstream_ssh 'gear remotedeploy'

      
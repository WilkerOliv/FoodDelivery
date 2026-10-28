const pendingActions = new Set();

/** Runs only the first action for a key until that action settles. */
export async function runOnce(key, action) {
  if (pendingActions.has(key)) return undefined;
  pendingActions.add(key);
  try {
    return await action();
  } finally {
    pendingActions.delete(key);
  }
}
